package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserRankDTO {

  @NotNull
  @Size(max = 100, min = 1)
  private String name;

  private String iconUrl;

  @NotNull private Double diamond;

  private String detail;
}
