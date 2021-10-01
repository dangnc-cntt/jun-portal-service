package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.BannerLink;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BannerCreateDTO {
  @NotNull private String bannerUrl;

  @NotNull private BannerLink linkTo;

  @NotNull private String linkParam1;

  @NotNull private String linkParam2;
}
