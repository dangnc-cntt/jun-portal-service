package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Order;
import com.jun.portalservice.domain.entities.types.OrderState;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/orders")
public class OrderController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<Order>> fillter(
      Pageable pageable,
      @RequestParam(required = false) Integer accountId,
      @RequestParam(required = false) String gte,
      @RequestParam(required = false) String lte,
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(orderService.fillter(accountId, gte, lte, pageable));
  }

  @GetMapping("{orderId}")
  public ResponseEntity<Order> findById(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable("orderId") Integer orderId) {
    validateToken(token);
    return ResponseEntity.ok(orderService.findById(orderId));
  }

  @PutMapping("{orderId}")
  public ResponseEntity<Order> update(
      @PathVariable("orderId") Long orderId,
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam() OrderState state) {
    validateToken(token);
    return ResponseEntity.ok(orderService.update(orderId, state));
  }
}
