package com.jun.portalservice.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GiftHistoryDTO {
  @NotNull private Integer currentTurn;

  @NotNull private Long luckySpinGiftId;

  @NotNull private String itemLabel;

  @NotNull private String itemValue;

  @NotNull private Boolean state = false;
}
