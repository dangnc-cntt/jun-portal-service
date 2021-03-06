package com.jun.portalservice.domain.entities.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "color")
public class Color extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "color_sequence";
  @Id private Integer id;

  @Field(name = "name")
  private String name;
}
