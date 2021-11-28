package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.types.PaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ExportDTO {
  @NotNull private String description;

  @NotNull private List<ProductView> products;

  @NotNull private PaymentMethod type;
}
