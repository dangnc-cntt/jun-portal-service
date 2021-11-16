package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.VoucherAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherAccountRepository extends MongoRepository<VoucherAccount, String> {
  VoucherAccount findVoucherAccountByAccountIdAndVoucherId(Integer accountId, Integer voucherId);
}
