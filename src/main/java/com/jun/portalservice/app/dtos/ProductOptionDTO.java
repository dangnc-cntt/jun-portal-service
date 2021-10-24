package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOptionDTO {
  private Color color;

  private Integer productId;

  private Size size;

  private Integer amount;
}
