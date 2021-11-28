package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.WarehouseExport;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WarehouseExportRepository
    extends MongoResourceRepository<WarehouseExport, Integer> {
  WarehouseExport findWarehouseExportById(Integer id);

  List<WarehouseExport> findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
      LocalDateTime gte, LocalDateTime lte);
}
