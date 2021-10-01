package com.jun.portalservice.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SortLuckySpinSlotDTO {
  @NotNull private List<String> ids;
}
