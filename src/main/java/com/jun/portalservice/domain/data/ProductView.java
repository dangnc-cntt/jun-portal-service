package com.jun.portalservice.domain.data;

import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Order;
import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.mongo.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductView {
  private Integer id;
  private String name;
  private Float cost_price;
  private String code;
  private List<Option> options = new ArrayList<>();

  @Data
  @NoArgsConstructor
  public static class Option {
    private Integer id;

    private Color color;

    private Size size;

    private Integer amount;

    public void assign(ProductOption option) {
      id = option.getId();
      color = option.getColor();
      size = option.getSize();
      amount = option.getAmount();
    }
  }

  public void assign(Order.ProductView productView) {
    id = productView.getId();
    name = productView.getName();
    cost_price = productView.getCostPrice();
    code = productView.getCode();
    if (productView.getOption() != null) {
      Option option = new Option();
      option.assign(productView.getOption());
      options.add(option);
    }
  }
}
