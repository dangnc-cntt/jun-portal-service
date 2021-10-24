package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Account;
import com.jun.portalservice.domain.repositories.base.MongoResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoResourceRepository<Account, Integer> {

  Account findAccountById(Integer accountId);

  Page<Account> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
