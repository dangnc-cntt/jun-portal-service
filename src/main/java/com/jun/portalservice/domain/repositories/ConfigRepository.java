package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, String> {
  Config findConfigByKey(String key);

  void deleteByKey(String key);
}
