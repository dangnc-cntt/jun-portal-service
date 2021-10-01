package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Account;
import com.jun.portalservice.domain.utils.CacheKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AccountStorage extends BaseStorage {

  public Account save(Account account) {
    account = accountRepository.save(account);
    processCache(account);
    return account;
  }

  private void processCache(Account account) {
    caching.put(CacheKey.genAccountByIdKey(account.getId()), account);
    caching.put(CacheKey.genAccountByUserNameKey(account.getUsername()), account);
  }

  public Account findAccountById(String accountId) {
    return accountRepository.findAccountById(accountId);
  }

  public Page<Account> findAll(Pageable pageable) {
    return accountRepository.findAllByOrderByCreatedAtDesc(pageable);
  }
}
