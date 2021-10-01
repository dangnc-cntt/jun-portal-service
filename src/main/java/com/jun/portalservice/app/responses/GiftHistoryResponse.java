package com.jun.portalservice.app.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiftHistoryResponse {
  private String id;
  private String accountId;
  private String username;
  private String luckySpinGiftId;
  private String itemLabel;
  private String itemValue;
  private Boolean state = false;
  private LocalDateTime giftDay;
}
