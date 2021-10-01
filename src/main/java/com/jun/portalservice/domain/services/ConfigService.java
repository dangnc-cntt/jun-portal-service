package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Config;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ConfigService extends BaseService {

  public PageResponse list(Pageable pageable) {
    return PageResponse.createFrom(configRepository.findAll(pageable));
  }

  public Config detail(String key) {
    return configRepository.findConfigByKey(key);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void create(ConfigDTO dto) {

    Config config = detail(dto.getKey());
    if (config == null) {
      config = modelMapper.toConfig(dto);
      config.setId((int) generateSequence(Config.SEQUENCE_NAME));
    } else {
      config.setValue(dto.getValue());
    }
    configStorage.save(config);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void delete(String key) {
    configStorage.delete(key);
  }

  //  public List<Reward> getRewardWeek() {
  //    Config configWeek = detail(Constants.REWARD_WEEK_CONFIG);
  //    if (configWeek == null) {
  //      return null;
  //    }
  //
  //    try {
  //      return JsonParser.arrayList(configWeek.getValue(), Reward.class);
  //    } catch (Exception e) {
  //      return null;
  //    }
  //  }

  //  public List<Reward> getRewardMonth() {
  //    Config configMonth = detail(Constants.REWARD_MONTH_CONFIG);
  //    if (configMonth == null) {
  //      return null;
  //    }
  //    try {
  //      return JsonParser.arrayList(configMonth.getValue(), Reward.class);
  //    } catch (Exception e) {
  //      return null;
  //    }
  //  }
}
