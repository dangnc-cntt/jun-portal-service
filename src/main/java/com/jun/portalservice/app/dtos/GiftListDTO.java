package com.jun.portalservice.app.dtos;

import lombok.Data;

@Data
public class GiftListDTO {
  private Long giftId;
  private String label;
  private String imageUrl;
  private Integer position;
}
