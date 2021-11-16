package com.jun.portalservice.domain.storages;

import com.jun.portalservice.app.responses.ProductResponse;
import com.jun.portalservice.domain.entities.mongo.Product;
import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.types.ProductState;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductStorage extends BaseStorage {

  public Product save(Product product) {
    product = productRepository.save(product);

    if (product.getState() != ProductState.ACTIVE) {
      processCache(product);
    }
    return product;
  }

  public Product findById(Integer productId) {
    Product product = caching.get(CacheKey.genProductKey(productId), Product.class);
    if (product == null) {
      product = new Product();
      product = productRepository.findProductById(productId);

      if (product != null) {
        caching.put(CacheKey.genProductKey(productId), product);
      }
    }
    return product;
  }

  public void processCache(Product product) {

    caching.put(CacheKey.genProductKey(product.getId()), product);
    if (product.getIsHot()) {
      List<Product> hotProductList = productRepository.findByIsHot(true);
      if (hotProductList != null && hotProductList.size() > 0) {
        caching.put(CacheKey.genListProductIsHotKey(), toProductResponses(hotProductList));
      }
    }

    List<Product> productListCategory =
        productRepository.findByCategoryIdAndState(product.getCategoryId(), ProductState.ACTIVE);
    if (productListCategory != null && productListCategory.size() > 0) {
      caching.put(
          CacheKey.genListProductByCategoryKey(product.getCategoryId()),
          toProductResponses(productListCategory));
    }
  }

  public List<ProductResponse> toProductResponses(List<Product> productList) {
    List<ProductResponse> responseList = new ArrayList<>();

    for (Product item : productList) {
      ProductResponse productResponse = modelMapper.toProductResponse(item);
      List<ProductOption> optionList = productOptionRepository.findByProductId(item.getId());
      productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
      responseList.add(productResponse);
    }
    return responseList;
  }

  public void delete(Product product) {
    // Todo delete all option
    productRepository.delete(product);

    caching.del(CacheKey.genProductKey(product.getId()));
    caching.del(CacheKey.genListProductByCategoryKey(product.getCategoryId()));
    caching.del(CacheKey.genListProductIsHotKey());
  }
}
