package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.mongo.WarehouseExport;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

@Component
public class WarehouseExportStorage extends BaseStorage {
  //    genListOptionProductIdKey

  public WarehouseExport save(WarehouseExport warehouseExport) {
    warehouseExport = warehouseExportRepository.save(warehouseExport);
    for (ProductView product : warehouseExport.getProducts()) {
      caching.del(CacheKey.genListOptionProductIdKey(product.getId()));
    }
    return warehouseExport;
  }
}
