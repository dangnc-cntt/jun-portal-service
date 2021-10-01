package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.domain.entities.types.BannerLink;
import com.jun.portalservice.domain.entities.types.BannerState;
import com.jun.portalservice.domain.entities.types.PositionBanner;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "banner")
@Data
public class Banner extends BaseEntity {

  @Transient public static final String SEQUENCE_NAME = "Banner_id_sequence";

  @Id private Integer id;

  @Field(name = "banner_url")
  private String bannerUrl;

  @Field(name = "position")
  private PositionBanner position;

  @Field(name = "state")
  private BannerState state;

  // TODO:Make a class config
  @Field(name = "linkTo")
  private BannerLink linkTo = BannerLink.LEADER_BOARD;

  @Field(name = "linkParam1")
  private String linkParam1 = "";

  @Field(name = "linkParam2")
  private String linkParam2 = "";

  @Field private Integer sort;
}
