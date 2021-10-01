package com.jun.portalservice.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SortBannerDTO {
  @NotNull private List<Integer> ids;
}
