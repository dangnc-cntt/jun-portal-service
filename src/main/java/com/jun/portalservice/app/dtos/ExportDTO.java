package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.data.ProductView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ExportDTO {
  @NotNull private String description;

  private List<ProductView> products;

  @NotNull private Boolean isOnline;

  private Long orderId;
}
