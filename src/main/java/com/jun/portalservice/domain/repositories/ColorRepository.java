package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends MongoRepository<Color, Integer> {
  Size findSizeById(Integer id);

  Size findSizeByCode(String code);
}
