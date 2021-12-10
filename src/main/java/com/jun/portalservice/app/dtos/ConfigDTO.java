package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.ConfigType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConfigDTO {

  @NotNull
  @Size(min = 1, max = 200)
  private String key;

  @NotNull private String value;

  @NotNull private ConfigType type;
}
