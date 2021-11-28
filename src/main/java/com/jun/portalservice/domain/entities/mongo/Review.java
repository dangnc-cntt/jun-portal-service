package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.domain.entities.types.ReviewState;
import jun.message.ReviewMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "review")
@Data
@NoArgsConstructor
public class Review extends BaseEntity {
  @Id private String id;

  @Field(name = "created_by")
  private Integer createdBy;

  @Field(name = "content")
  private String content;

  @Field(name = "star")
  private Integer star;

  @Field(name = "state")
  private ReviewState state;

  @Field(name = "product_id")
  private Integer productId;

  public Review(ReviewMessage message) {
    createdBy = message.getCreatedBy();
    content = message.getContent().equals("1") ? null : message.getContent();
    star = message.getStar();
    state = ReviewState.NOT_APPROVED;
    productId = message.getProductId();
  }
}
