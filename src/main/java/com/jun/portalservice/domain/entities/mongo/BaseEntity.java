package com.jun.portalservice.domain.entities.mongo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
  @Field(name = "created_at")
  @CreatedDate
  protected LocalDateTime createdAt = LocalDateTime.now();

  @Field(name = "updated_at")
  @LastModifiedDate
  protected LocalDateTime updatedAt = LocalDateTime.now();
}
