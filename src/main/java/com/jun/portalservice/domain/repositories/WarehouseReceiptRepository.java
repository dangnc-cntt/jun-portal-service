package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.WarehouseReceipt;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WarehouseReceiptRepository
    extends MongoResourceRepository<WarehouseReceipt, Integer> {
  WarehouseReceipt findWarehouseReceiptById(Integer id);

  List<WarehouseReceipt> findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
      LocalDateTime gte, LocalDateTime lte);
}
