package com.jun.portalservice.domain.data;

import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductView {
  private Integer id;
  private String name;
  private Float cost_price;
  private String code;
  private List<Option> options;

  @Data
  public static class Option {
    private Integer id;

    private Color color;

    private Size size;

    private Integer amount;
  }
}
