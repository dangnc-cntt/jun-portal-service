package com.jun.portalservice.domain.repositories;

import com.jun.portalservice.domain.entities.mongo.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

  Account findAccountById(String accountId);

  Page<Account> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
