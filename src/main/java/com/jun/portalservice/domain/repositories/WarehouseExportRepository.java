package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.WarehouseExport;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;

public interface WarehouseExportRepository
    extends MongoResourceRepository<WarehouseExport, Integer> {
  WarehouseExport findWarehouseExportById(Integer id);
}
