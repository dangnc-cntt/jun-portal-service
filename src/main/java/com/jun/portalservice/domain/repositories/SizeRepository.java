package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends MongoRepository<Size, Integer> {
  Size findSizeById(Integer id);

  Size findByName(String name);
}
