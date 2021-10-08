package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Category;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoResourceRepository<Category, Integer> {
  Category findCategoriesById(Integer id);
}
