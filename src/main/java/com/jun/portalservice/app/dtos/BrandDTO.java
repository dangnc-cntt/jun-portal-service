package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandDTO {
  private String name;

  private String description;

  private String logoUrl;
}
