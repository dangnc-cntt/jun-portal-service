package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderStorage extends BaseStorage {

  public Order save(Order order) {
    return orderRepository.save(order);
  }
}
