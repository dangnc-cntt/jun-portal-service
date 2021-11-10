package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.WarehouseReceipt;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;

public interface WarehouseReceiptRepository
    extends MongoResourceRepository<WarehouseReceipt, Integer> {
  WarehouseReceipt findWarehouseReceiptById(Integer id);
}
