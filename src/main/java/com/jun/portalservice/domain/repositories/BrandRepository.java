package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Brand;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends MongoResourceRepository<Brand, Integer> {
  Brand findBrandById(Integer id);
}
