package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AccountStorage extends BaseStorage {

  public Account save(Account account) {
    account = accountRepository.save(account);
    return account;
  }

  public Account findAccountById(String accountId) {
    return accountRepository.findAccountById(accountId);
  }

  public Page<Account> findAll(Pageable pageable) {
    return accountRepository.findAllByOrderByCreatedAtDesc(pageable);
  }
}
