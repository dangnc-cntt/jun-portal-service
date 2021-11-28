package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.entities.types.ConfigType;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class ConfigService extends BaseService {

  public PageResponse<Config> list(String key, ConfigType type, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));
    if (key != null) {
      andConditions.add(Criteria.where("key").is(key));
    }
    if (type != null) {
      andConditions.add(Criteria.where("type").is(type));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Config> configPage = configRepository.findAll(query, pageable);
    return PageResponse.createFrom(configPage);
  }

  public Config detail(String key) {
    Config config = configRepository.findConfigByKey(key);
    if (config == null) {
      throw new ResourceNotFoundException("No config found!");
    }
    return config;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Config create(ConfigDTO dto) {

    Config config = detail(dto.getKey());
    if (config == null) {
      config = modelMapper.toConfig(dto);
      config.setId((int) generateSequence(Config.SEQUENCE_NAME));
    } else {
      config.setValue(dto.getValue());
    }
    return configStorage.save(config);
  }
  //
  //  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  //  public Config update(ConfigDTO dto) {
  //
  //    Config config = detail(dto.getKey());
  //    if (config == null) {
  //      config = modelMapper.toConfig(dto);
  //      config.setId((int) generateSequence(Config.SEQUENCE_NAME));
  //    } else {
  //      config.setValue(dto.getValue());
  //    }
  //    return configStorage.save(config);
  //  }

  private void validateValue(ConfigType type, String value) {
    switch (type) {
      case BOOLEAN:
        Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
          throw new BadRequestException("Value is not BOOLEAN type!");
        }
        break;
      case DOUBLE:
        try {
          Double.parseDouble(value);
        } catch (NumberFormatException exception) {
          throw new BadRequestException("Value is not DOUBLE type!");
        }

        break;
      case FLOAT:
        try {
          Float.parseFloat(value);
        } catch (NumberFormatException exception) {
          throw new BadRequestException("Value is not FLOAT type!");
        }

        break;
      case INTEGER:
        try {
          Integer.parseInt(value);
        } catch (NumberFormatException exception) {
          throw new BadRequestException("Value is not INTEGER type!");
        }

        break;
      case LONG:
        try {
          Long.parseLong(value);
        } catch (NumberFormatException exception) {
          throw new BadRequestException("Value is not LONG type!");
        }

        break;
    }
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Config update(ConfigDTO dto) {

    Config config = detail(dto.getKey());
    if (config == null) {
      throw new ResourceNotFoundException("No config found!");
    }
    config.setValue(dto.getValue());
    return configStorage.save(config);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean delete(String key) {
    configStorage.delete(key);
    return true;
  }
}
