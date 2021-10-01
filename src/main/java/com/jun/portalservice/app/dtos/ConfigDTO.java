package com.jun.portalservice.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConfigDTO {

  @NotNull
  @Size(min = 1, max = 200)
  private String key;

  @NotNull private String value;
}
