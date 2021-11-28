package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.mongo.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RevenueResponse {
  private Integer totalOrder = 0;
  private Integer totalProduct = 0;
  private Double total = 0D;
  private Double discount = 0D;
  private Double actual = 0D;
  private Double spending = 0D;
  private PageResponse<Order> orders;
}
