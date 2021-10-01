package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.app.dtos.VoucherDTO;
import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.entities.types.VoucherType;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "voucher")
@NoArgsConstructor
public class Voucher extends BaseEntity {

  @Id private String id;

  @Field(name = "name")
  private String name;

  @Field(name = "thumb")
  private String thumbUrl;

  @Field(name = "expire")
  private LocalDateTime expire;

  @Field(name = "type")
  private VoucherType type;

  @Field(name = "bean_price")
  private Long beanPrice;

  @Field(name = "diamond_price")
  private Long diamondPrice;

  @Field(name = "amount")
  private Integer amount;

  @Field(name = "remain")
  private Integer remain;

  @Field(name = "state")
  private VoucherState state;

  @Field(name = "hot")
  private Boolean isHot;

  @Field(name = "detail")
  private String detail;

  @Field(name = "created_by")
  private String createdBy;

  @Field(name = "updated_by")
  private String updatedBy;

  @Field(name = "activated_at")
  protected LocalDateTime activatedAt;

  public void from(VoucherDTO dto) {
    setName(dto.getName());
    setThumbUrl(dto.getThumbUrl() == null ? null : dto.getThumbUrl());
    setExpire(dto.getExpire());
    setType(dto.getType());

    checkType(dto);

    setBeanPrice(dto.getBeanPrice());
    setDiamondPrice(dto.getDiamondPrice());
    setAmount(dto.getAmount());
    setRemain(dto.getAmount());
    setDetail(dto.getDetail() == null ? null : dto.getDetail());
    setIsHot(dto.getIsHot());
  }

  public void checkType(VoucherDTO dto) {
    switch (dto.getType()) {
      case BOTH:
        if (dto.getBeanPrice() == null
            || dto.getDiamondPrice() == null
            || dto.getDiamondPrice() == 0
            || dto.getBeanPrice() == 0) {
          throw new BadRequestException("Bean and diamond must be greater than 0 in type BOTH!");
        }
        break;
      case BEAN:
        if (dto.getBeanPrice() == null || dto.getBeanPrice() == 0) {
          throw new BadRequestException("Bean must be greater than 0 in type BEAN!");
        }
        dto.setDiamondPrice(0L);
        break;
      case DIAMOND:
        if (dto.getDiamondPrice() == null || dto.getDiamondPrice() == 0) {
          throw new BadRequestException("Bean must be greater than 0 in type BEAN!");
        }
        dto.setBeanPrice(0L);
        break;
      case FREE:
        dto.setBeanPrice(0L);
        dto.setDiamondPrice(0L);
        break;
      default:
        throw new BadRequestException("Type must be BOTH or DIAMOND or BEAN or FREE!");
    }
  }
}
