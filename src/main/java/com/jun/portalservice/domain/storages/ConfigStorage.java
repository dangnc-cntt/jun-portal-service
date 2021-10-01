package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ConfigStorage extends BaseStorage {

  public Page<Banner> findAll(Pageable pageable) {
    return bannerRepository.findAll(pageable);
  }

  public Config detail(String key) {
    return configRepository.findConfigByKey(key);
  }

  public Config save(Config config) {
    config = configRepository.save(config);

    caching.put(CacheKey.genBannerConfigKey(config.getKey()), config.getValue(), Integer.MAX_VALUE);
    return config;
  }

  public boolean delete(String key) {
    configRepository.deleteByKey(key);
    caching.del(CacheKey.genBannerConfigKey(key));
    return true;
  }
}
