package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;

public interface ConfigRepository extends MongoResourceRepository<Config, Integer> {
  Config findConfigByKey(String key);

  void deleteByKey(String key);
}
