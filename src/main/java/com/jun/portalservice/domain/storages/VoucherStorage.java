package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.entities.mongo.Voucher;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class VoucherStorage extends BaseStorage {

  public Voucher save(Voucher voucher) throws Exception {
    voucher = voucherRepository.save(voucher);

    if (voucher.getState() == VoucherState.ACTIVE) {
      caching.put(CacheKey.genActiveVoucherByIdKey(voucher.getId()), voucher);

      List<Voucher> voucherList = caching.getList(CacheKey.genVoucherListKey(), Voucher.class);

      if (voucherList != null && voucherList.size() > 0) {
        voucherList.removeIf(checkVoucher(voucher.getId()));
      } else {
        voucherList = new ArrayList<>();
      }

      voucherList.add(voucher);
      caching.put(CacheKey.genVoucherListKey(), voucherList);
    } else {
      caching.del(CacheKey.genActiveVoucherByIdKey(voucher.getId()));
      List<Voucher> voucherList = caching.getList(CacheKey.genVoucherListKey(), Voucher.class);

      if (voucherList != null && voucherList.size() > 0) {
        voucherList.removeIf(checkVoucher(voucher.getId()));
      }
      caching.put(CacheKey.genVoucherListKey(), voucherList);
    }
    return voucher;
  }

  public Predicate<Voucher> checkVoucher(String voucherId) {
    return p -> p.getId() == voucherId;
  }

  public Page<Voucher> findAll(Query condition, Pageable pageable) {
    return voucherRepository.findAll(condition, pageable);
  }

  public Page<Voucher> findAll(Pageable pageable) {
    return voucherRepository.findAllByOrderByCreatedAtDesc(pageable);
  }

  public Voucher findById(String voucherId) {
    return voucherRepository.findVoucherById(voucherId);
  }

  public Page<Voucher> findByUserId(String userId, Pageable pageable) {
    return voucherRepository.findByCreatedByOrderByCreatedAtDesc(userId, pageable);
  }

  public boolean delete(String voucherId) {
    voucherRepository.deleteById(voucherId);
    return true;
  }
}
