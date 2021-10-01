package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.LuckySpinGiftType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LuckySpinGiftDTO {

  @NotNull private String itemLabel;

  private String itemValue;

  @NotNull private String imageUrl;

  @NotNull private String displayName;

  @NotNull private LuckySpinGiftType type;

  @NotNull private Integer ratePoint;

  @NotNull private Integer maxPoint;

  @NotNull private Integer currentPoint;
}
