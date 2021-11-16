package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OptionResponse {
  private Integer id;

  private Color color;

  private Size size;

  private Integer amount;
}
