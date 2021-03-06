package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends MongoResourceRepository<ProductOption, Integer> {
  List<ProductOption> findByProductId(int productId);

  Page<ProductOption> findAllByOrderByIdDesc(Pageable pageable);

  ProductOption findProductOptionByIdAndProductId(int id, Integer productId);
}
