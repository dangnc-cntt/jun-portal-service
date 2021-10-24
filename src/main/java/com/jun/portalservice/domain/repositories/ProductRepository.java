package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Product;
import com.jun.portalservice.domain.entities.types.ProductState;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoResourceRepository<Product, Integer> {
  Product findByCode(String code);

  Product findProductById(int id);

  List<Product> findByCategoryIdAndState(int categoryId, ProductState state);

  List<Product> findByIsHot(Boolean isHot);
}
