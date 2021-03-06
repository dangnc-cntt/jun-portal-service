package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.types.ProductState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductResponse {

  private Integer id;

  private String code;

  private String name;

  private List<String> imageUrls;

  private String description;

  private ProductState state;

  private Integer categoryId;

  private Boolean isHot;

  private Float costPrice;

  private Float price;

  private Float discount;

  private Float star = 0F;

  private Integer createdBy;

  private Integer brandId;

  List<ProductOption> optionList;
}
