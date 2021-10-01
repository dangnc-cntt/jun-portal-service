package com.jun.portalservice.domain.entities.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequence")
@Data
public class Sequence {
  @Id private String id;

  private Long seq;
}
