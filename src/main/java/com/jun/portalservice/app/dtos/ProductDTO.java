package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.types.ProductState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {
  @NotNull private String code;

  @NotNull private String name;

  @NotNull private List<String> imageUrls;

  private String description;

  @NotNull private ProductState state;

  @NotNull private Integer categoryId;

  @NotNull private Boolean isHot;

  @NotNull private Float costPrice;

  @NotNull private Float price;

  private Float discount;

  @NotNull private List<ProductOption> optionList;
}
