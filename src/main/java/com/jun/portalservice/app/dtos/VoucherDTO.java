package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VoucherDTO {
  @NotNull private String name;
  private String thumbUrl;
  @NotNull private LocalDateTime expire;
  @NotNull private VoucherType type;
  private Long beanPrice;
  private Long diamondPrice;
  @NotNull private Boolean isHot;
  @NotNull private Integer amount;
  private String detail;
  private VoucherState state;
}
