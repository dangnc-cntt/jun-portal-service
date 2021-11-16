package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class VoucherStorage extends BaseStorage {

  public Voucher save(Voucher voucher) throws Exception {
    deleteCache();
    return voucherRepository.save(voucher);
  }

  public Predicate<Voucher> checkVoucher(Integer voucherId) {
    return p -> Objects.equals(p.getId(), voucherId);
  }

  public void deleteCache() {
    caching.del(new ArrayList<>(caching.keys("jun::active:voucher:list:account:*")));
  }

  public Page<Voucher> findAll(Query condition, Pageable pageable) {
    return voucherRepository.findAll(condition, pageable);
  }

  public Page<Voucher> findAll(Pageable pageable) {
    return voucherRepository.findAllByOrderByCreatedAtDesc(pageable);
  }

  public Voucher findById(Integer voucherId) {
    return voucherRepository.findVoucherById(voucherId);
  }

  public Page<Voucher> findByUserId(String userId, Pageable pageable) {
    return voucherRepository.findByCreatedByOrderByCreatedAtDesc(userId, pageable);
  }

  public boolean delete(Integer voucherId) {
    voucherRepository.deleteById(voucherId);
    deleteCache();
    return true;
  }
}
