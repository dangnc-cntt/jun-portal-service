package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SizeDTO {
  @NotNull private String name;
}
