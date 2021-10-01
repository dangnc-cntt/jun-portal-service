package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Voucher;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends MongoResourceRepository<Voucher, String> {

  Voucher findVoucherById(String voucherId);

  Page<Voucher> findByCreatedByOrderByCreatedAtDesc(String userId, Pageable pageable);

  Page<Voucher> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
