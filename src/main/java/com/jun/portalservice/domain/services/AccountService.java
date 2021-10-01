package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.responses.AccountResponse;
import com.jun.portalservice.app.responses.Metadata;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Account;
import com.jun.portalservice.domain.entities.types.AccountState;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.exceptions.AccountNotExistsException;
import com.jun.portalservice.domain.exceptions.AuthenticationCodeNotExistsException;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService extends BaseService {

  public PageResponse<AccountResponse> findAll(Pageable pageable) {
    Page<Account> accountPage = accountStorage.findAll(pageable);

    if (accountPage == null || accountPage.getContent().size() == 0) {
      throw new ResourceNotFoundException("No account found!");
    }

    List<AccountResponse> responseList = new ArrayList<>();

    for (Account account : accountPage.getContent()) {
      AccountResponse accountResponse = modelMapper.toAccountResponse(account);
      responseList.add(accountResponse);
    }
    return new PageResponse<>(responseList, Metadata.createFrom(accountPage));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public AccountResponse updateStateUser(String accountId, AccountState state, UserRole userRole) {
    if (userRole != UserRole.ADMIN) {
      throw new AuthenticationCodeNotExistsException();
    }
    if (state != AccountState.ACTIVATED && state != AccountState.BANNED) {
      throw new BadRequestException("State must be ACTIVATED or BANNED!");
    }
    Account account = accountStorage.findAccountById(accountId);
    if (account == null) {
      throw new AccountNotExistsException();
    }

    account.setState(state);
    account = accountStorage.save(account);

    return modelMapper.toAccountResponse(account);
  }
}
