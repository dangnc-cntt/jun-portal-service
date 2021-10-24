package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ColorDTO {
  @NotNull private String name;
}
