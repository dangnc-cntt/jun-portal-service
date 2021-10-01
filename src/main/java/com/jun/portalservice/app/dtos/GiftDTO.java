package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GiftDTO {
  private String giftId;
  private String label;
  private String imageUrl;
  private String displayName;
}
