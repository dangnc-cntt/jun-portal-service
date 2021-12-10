package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Order;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoResourceRepository<Order, Long> {
  Page<Order> findByOrderedByOrderByCreatedAt(Integer accountId, Pageable pageable);

  Order findByIdAndOrderedBy(Long id, Integer accountId);

  Order findOrderById(Long id);

  List<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAt(
      LocalDateTime gte, LocalDateTime lte);

  Page<Order> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAt(
      LocalDateTime gte, LocalDateTime lte, Pageable pageable);
}
