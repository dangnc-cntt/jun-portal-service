package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

@Component
public class ConfigStorage extends BaseStorage {

  public Config detail(String key) {
    return configRepository.findConfigByKey(key);
  }

  public Config save(Config config) {
    config = configRepository.save(config);

    caching.put(CacheKey.genConfigKey(config.getKey()), config, 36000);
    return config;
  }

  public boolean delete(String key) {
    configRepository.deleteByKey(key);
    caching.del(CacheKey.genConfigKey(key));
    return true;
  }
}
