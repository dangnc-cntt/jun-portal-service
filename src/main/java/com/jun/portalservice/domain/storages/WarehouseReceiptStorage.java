package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.mongo.WarehouseReceipt;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

@Component
public class WarehouseReceiptStorage extends BaseStorage {
  public WarehouseReceipt save(WarehouseReceipt warehouseReceipt) {
    warehouseReceipt = warehouseReceiptRepository.save(warehouseReceipt);
    for (ProductView product : warehouseReceipt.getProducts()) {
      caching.del(CacheKey.genListOptionProductIdKey(product.getId()));
    }
    return warehouseReceipt;
  }
}
