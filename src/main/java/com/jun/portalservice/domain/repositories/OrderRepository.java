package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
  Page<Order> findByOrderedByOrderByCreatedAt(Integer accountId, Pageable pageable);

  Order findByIdAndOrderedBy(Long id, Integer accountId);

  Order findOrderById(Long id);
}
