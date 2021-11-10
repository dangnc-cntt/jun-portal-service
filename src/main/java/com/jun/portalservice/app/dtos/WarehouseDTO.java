package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.data.ProductView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class WarehouseDTO {

  @NotNull private String description;

  @NotNull List<ProductView> products;
}
