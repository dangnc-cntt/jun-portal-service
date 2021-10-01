package com.jun.portalservice.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class LuckySpinSlotDTO {
  @NotNull private List<GiftDTO> giftListDTO;
}
