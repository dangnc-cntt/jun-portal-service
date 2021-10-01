package com.jun.portalservice.domain.entities.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "lucky_spin_gift_set")
public class GiftSet {
  @Transient public static final String SEQUENCE_NAME = "lucky_spin_gift_set_sequence";

  @Field(name = "created_at")
  @CreatedDate
  public LocalDateTime createdAt;

  @Field(name = "updated_at")
  @LastModifiedDate
  public LocalDateTime updatedAt;

  @Id private Integer id;

  @Field(name = "name")
  private String name;

  @Field(name = "created_by")
  private String createdBy;

  @Field(name = "is_default")
  private Boolean isDefault;

  //    @OneToOne(cascade = CascadeType.ALL)
  //    @Field(name = "luckyspin_slot_id")
  //    private LuckySpinSlot luckySpinSlot;
}
