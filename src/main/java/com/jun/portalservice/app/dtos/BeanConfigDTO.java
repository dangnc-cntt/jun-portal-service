package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BeanConfigDTO {
  @NotNull
  @Size(min = 4, max = 50)
  private String code;

  @NotNull private Long bean;
}
