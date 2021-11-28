package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.app.dtos.BrandDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document(collection = "brand")
public class Brand extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "brand_sequence";

  @Id private Integer id;

  @Field(name = "name")
  private String name;

  @Field(name = "description")
  private String description;

  @Field(name = "logo_url")
  private String logoUrl;

  public void assign(BrandDTO dto) {
    name = dto.getName();
    description = dto.getDescription();
    logoUrl = dto.getLogoUrl();
  }
}
