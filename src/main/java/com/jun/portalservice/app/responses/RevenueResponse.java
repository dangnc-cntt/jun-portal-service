package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.mongo.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RevenueResponse {

  private Statistic statistic = new Statistic();
  private PageResponse<Order> orders ;
  private List<Day> days = new ArrayList<>();

  @Data
  @NoArgsConstructor
  public static class Day{
    private String date;
    private Statistic statistic;
  }

  @Data
  @NoArgsConstructor
  public static class Statistic{
    private Integer totalOrder = 0;
    private Integer totalProduct = 0;
    private Double total = 0D;
    private Double discount = 0D;
    private Double actual = 0D;
    private Double spending = 0D;
  }
}
