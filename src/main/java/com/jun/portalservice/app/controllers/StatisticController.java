package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.responses.RevenueResponse;
import com.jun.portalservice.app.responses.WarehouseStatisticsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/statistic")
public class StatisticController extends BaseController {
  @GetMapping("revenue")
  public ResponseEntity<RevenueResponse> revenueStatistic(
      @RequestParam() String gte,
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam() String lte,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(statisticService.statisticRevenue(gte, lte, pageable));
  }

  @GetMapping("warehouse")
  public ResponseEntity<WarehouseStatisticsResponse> warehouseStatistic(
      @RequestParam() String gte,
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam() String lte,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(statisticService.warehouseStatistics(gte, lte, pageable));
  }
}
