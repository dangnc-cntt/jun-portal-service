package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.ColorDTO;
import com.jun.portalservice.app.dtos.ProductDTO;
import com.jun.portalservice.app.dtos.SizeDTO;
import com.jun.portalservice.app.responses.Metadata;
import com.jun.portalservice.app.responses.OptionResponse;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.ProductResponse;
import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Product;
import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.mongo.Size;
import com.jun.portalservice.domain.exceptions.ResourceExitsException;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import com.jun.portalservice.domain.utils.CacheKey;
import com.jun.portalservice.domain.utils.Caching;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends BaseService {
  @Autowired protected Caching caching;

  public PageResponse<ProductResponse> filter(
      Integer categoryId, String code, String name, Boolean isHot, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));
    if (categoryId != null) {
      andConditions.add(Criteria.where("categoryId").is(categoryId));
    }
    if (StringUtils.isNotEmpty(code)) {
      andConditions.add(Criteria.where("code").regex(code, "i"));
    }
    if (StringUtils.isNotEmpty(code)) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }
    if (isHot != null) {
      andConditions.add(Criteria.where("name").is(isHot));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Product> productPage = productRepository.findAll(query, pageable);
    List<ProductResponse> productResponses = new ArrayList<>();
    if (productPage != null && productPage.getContent().size() > 0) {
      for (Product product : productPage.getContent()) {
        ProductResponse productResponse = modelMapper.toProductResponse(product);
        List<ProductOption> optionList = productOptionRepository.findByProductId(product.getId());
        productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
        productResponses.add(productResponse);
      }
    }
    return new PageResponse<>(productResponses, Metadata.createFrom(productPage));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public ProductResponse create(ProductDTO dto, int userId) {
    Product product = productRepository.findByCode(dto.getCode());
    if (product != null) {
      throw new ResourceExitsException("Product with code: " + dto.getCode() + " is exist!");
    }
    product = modelMapper.toProduct(dto);
    product.setId((int) generateSequence(Product.SEQUENCE_NAME));
    product.setCreatedBy(userId);

    product = productStorage.save(product);
    List<ProductOption> optionList = new ArrayList<>();
    if (dto.getOptionList().size() > 0) {
      for (ProductDTO.ProductOptionDTO option : dto.getOptionList()) {
        ProductOption productOption = new ProductOption();
        productOption.setProductId(product.getId());
        productOption.setId(
            option.getId() == null
                ? (int) generateSequence(ProductOption.SEQUENCE_NAME)
                : option.getId());
        productOption.setAmount(option.getAmount());
        productOption.setColor(option.getColor());
        productOption.setSize(option.getSize());
        optionList.add(productOption);
      }
    }

    ProductResponse response = modelMapper.toProductResponse(product);
    response.setOptionList(optionList);

    productOptionRepository.saveAll(optionList);

    List<ProductOption> productOptionList =
        productOptionRepository.findByProductId(product.getId());
    if (productOptionList != null) {
      caching.put(CacheKey.genListOptionProductIdKey(product.getId()), productOptionList);
    }

    return response;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public ProductResponse update(ProductDTO dto, int productId) {
    Product product = productRepository.findProductById(productId);
    if (product == null) {
      throw new ResourceNotFoundException("Product with id: " + productId + " is exist!");
    }
    ProductResponse response = new ProductResponse();
    product.from(dto);
    product = productStorage.save(product);

    List<ProductOption> optionList = new ArrayList<>();
    if (dto.getOptionList().size() > 0) {
      for (ProductDTO.ProductOptionDTO option : dto.getOptionList()) {
        ProductOption productOption = new ProductOption();
        productOption.setId(
            option.getId() == null
                ? (int) generateSequence(ProductOption.SEQUENCE_NAME)
                : option.getId());
        productOption.setProductId(product.getId());
        productOption.setAmount(option.getAmount());
        productOption.setColor(option.getColor());
        productOption.setSize(option.getSize());
        optionList.add(productOption);
      }
      //    dto.setOptionList(productOptionRepository.saveAll(dto.getOptionList()));
      response = modelMapper.toProductResponse(product);
      response.setOptionList(optionList);
      productOptionRepository.saveAll(optionList);
      List<ProductOption> options = productOptionRepository.findByProductId(product.getId());
      if (options != null) {
        caching.put(CacheKey.genListOptionProductIdKey(product.getId()), options);
      }
    }
    return response;
  }

  public ProductResponse findById(int productId) {
    Product product = productRepository.findProductById(productId);
    if (product == null) {
      throw new ResourceNotFoundException("Product with id: " + productId + " is exist!");
    }
    List<ProductOption> optionList = productOptionRepository.findByProductId(product.getId());

    ProductResponse productResponse = modelMapper.toProductResponse(product);
    productResponse.setOptionList(optionList);
    return productResponse;
  }

  public Color createColor(ColorDTO dto) {
    Color color = colorRepository.findByName(dto.getName());
    if (color != null) {
      throw new ResourceExitsException("This color is exist!");
    }

    color = new Color();
    color.setId((int) generateSequence(Color.SEQUENCE_NAME));
    color.setName(dto.getName());
    color = colorRepository.save(color);
    return color;
  }

  public Color findColorById(int colorId) {
    Color color = colorRepository.findColorById(colorId);
    if (color == null) {
      throw new ResourceNotFoundException("No color found!");
    }

    return color;
  }

  public Size findSizeById(int sizeId) {
    Size size = sizeRepository.findSizeById(sizeId);
    if (size == null) {
      throw new ResourceNotFoundException("No color found!");
    }

    return size;
  }

  public Size createSize(SizeDTO dto) {
    Size size = sizeRepository.findByName(dto.getName());
    if (size != null) {
      throw new ResourceExitsException("This color is exist!");
    }

    size = new Size();
    size.setId((int) generateSequence(Size.SEQUENCE_NAME));
    size.setName(dto.getName());
    size = sizeRepository.save(size);
    return size;
  }

  public List<Size> getAllSize() {
    List<Size> sizes = sizeRepository.findAll();
    if (sizes.size() < 1) {
      throw new ResourceNotFoundException("No size found!");
    }
    return sizes;
  }

  public List<Color> getAllColor() {
    List<Color> colors = colorRepository.findAll();
    if (colors.size() < 1) {
      throw new ResourceNotFoundException("No size found!");
    }
    return colors;
  }

  public List<OptionResponse> findOptionByProductId(Integer prodId) {
    return modelMapper.toOptionResponse(productOptionRepository.findByProductId(prodId));
  }
}
