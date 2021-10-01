package com.jun.portalservice.domain.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BlackListEmail {

  @Value("${black-list}")
  private List<String> blackList;
}
