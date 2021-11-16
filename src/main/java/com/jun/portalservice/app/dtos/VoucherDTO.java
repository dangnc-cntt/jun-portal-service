package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class VoucherDTO {
  @NotNull private String name;
  @NotNull private String imageUrl;
  @NotNull private String code;
  @NotNull private Double discount;
  @NotNull private VoucherType type;
  @NotNull private VoucherState state;
  @NotNull private String description;
  @NotNull private String date;
}
