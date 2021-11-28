package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.mongo.Product;
import com.jun.portalservice.domain.entities.mongo.ProductOption;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WarehouseStatisticsResponse {
  private Integer receipt = 0;
  private Integer export = 0;
  private PageResponse<ProductStatistic> products;

  @Data
  @NoArgsConstructor
  public static class ProductStatistic {
    private Integer id;

    private String code;

    private String name;

    private List<String> imageUrls;

    private String description;

    private String color;

    private String size;

    private Integer amount;

    public void assign(ProductOption option, Product product) {
      id = product.getId();
      code = product.getCode();
      name = product.getName();
      imageUrls = product.getImageUrls();
      description = product.getDescription();
      color = option.getColor().getName();
      size = option.getSize().getName();
      amount = option.getAmount();
    }
  }
}
