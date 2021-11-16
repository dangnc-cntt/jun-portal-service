package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "voucher")
@NoArgsConstructor
public class Voucher extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "voucher_sequence";

  @Id private Integer id;

  @Field(name = "name")
  private String name;

  @Field(name = "image_url")
  private String imageUrl;

  @Field(name = "expiry_date")
  private LocalDateTime expiryDate;

  @Field(name = "code")
  private String code;

  @Field(name = "discount")
  private Double discount;

  @Field(name = "type")
  private VoucherType type;

  @Field(name = "state")
  private VoucherState state;

  @Field(name = "description")
  private String description;

  @Field(name = "created_by")
  private Integer createdBy;

  //  public void checkType(VoucherDTO dto) {
  //    switch (dto.getType()) {
  //      case PERCENT:
  //        break;
  //      default:
  //        throw new BadRequestException("Type must be BOTH or DIAMOND or BEAN or FREE!");
  //    }
  //  }
}
