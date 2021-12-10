package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.BrandDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Brand;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService extends BaseService {
  public PageResponse<Brand> filter(String name, Integer id, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));
    if (id != null) {
      andConditions.add(Criteria.where("id").is(id));
    }

    if (name != null) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Brand> brandPage = brandRepository.findAll(query, pageable);

    return PageResponse.createFrom(brandPage);
  }

  public Brand findById(int brandId) {
    Brand brand = brandRepository.findBrandById(brandId);
    if (brand == null) {
      throw new ResourceNotFoundException("No brand found!");
    }
    return brand;
  }

  public List<Brand> findAll() {
    List<Brand> brands = brandRepository.findAll();
    if (brands.size() == 0) {
      throw new ResourceNotFoundException("No brand found!");
    }
    return brands;
  }

  public Brand create(BrandDTO dto) {
    Brand brand = modelMapper.toBrand(dto);
    brand.setId((int) generateSequence(Brand.SEQUENCE_NAME));

    return brandRepository.save(brand);
  }

  public Brand update(BrandDTO dto, int brandId) {
    Brand brand = brandRepository.findBrandById(brandId);

    if (brand == null) {
      throw new ResourceNotFoundException("No brand found!");
    }
    brand.assign(dto);
    return brandRepository.save(brand);
  }
}
