package com.jun.portalservice.domain.data;

import com.jun.portalservice.domain.entities.mongo.Banner;
import com.jun.portalservice.domain.entities.types.BannerLink;
import lombok.Data;

@Data
public class BannerConfig {
  private String bannerUrl;
  private BannerLink linkTo;
  private String linkParam1;
  private String linkParam2;

  public BannerConfig assignFrom(Banner banner) {
    bannerUrl = banner.getBannerUrl();
    linkTo = banner.getLinkTo();
    linkParam1 = banner.getLinkParam1();
    linkParam2 = banner.getLinkParam2();
    return this;
  }
}
