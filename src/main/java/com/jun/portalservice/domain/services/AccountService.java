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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService extends BaseService {

  public PageResponse<AccountResponse> filter(
      Integer id, String fullName, String phone, Pageable pageable) {

    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));

    if (StringUtils.isNotEmpty(fullName)) {
      andConditions.add(Criteria.where("fullName").regex(fullName, "i"));
    }
    if (id != null) {
      andConditions.add(Criteria.where("id").is(id));
    }
    if (StringUtils.isNotEmpty(phone)) {
      andConditions.add(Criteria.where("phoneNumber").regex(phone, "i"));
    }
    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    Page<Account> accountPage = accountRepository.findAll(query, pageable);

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

  public AccountResponse findById(Integer accountId) {
    Account account = accountRepository.findAccountById(accountId);
    if (account == null) {
      throw new AccountNotExistsException();
    }

    return modelMapper.toAccountResponse(account);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public AccountResponse updateStateUser(Integer accountId, AccountState state, UserRole userRole) {
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
