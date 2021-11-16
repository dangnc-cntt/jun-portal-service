package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.VoucherDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Account;
import com.jun.portalservice.domain.entities.mongo.Voucher;
import com.jun.portalservice.domain.entities.mongo.VoucherAccount;
import com.jun.portalservice.domain.entities.types.GiveVoucherType;
import com.jun.portalservice.domain.entities.types.VoucherState;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService extends BaseService {

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Voucher create(VoucherDTO dto, Integer userId) throws Exception {
    if (dto == null) {
      throw new BadRequestException("RequestBody is null!");
    }
    if (userId == null) {
      throw new BadRequestException("UserId is null!");
    }

    Voucher voucher = new Voucher();
    voucher = modelMapper.toVoucher(dto);
    voucher.setExpiryDate(
        LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
    voucher.setCreatedBy(userId);
    voucher.setId((int) generateSequence(Voucher.SEQUENCE_NAME));
    return voucherStorage.save(voucher);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Voucher update(VoucherDTO dto, Integer voucherId, Integer userId) throws Exception {
    if (dto == null) {
      throw new BadRequestException("RequestBody is null!");
    }
    if (userId == null) {
      throw new BadRequestException("UserId is null!");
    }
    if (voucherId == null) {
      throw new BadRequestException("VoucherId is null!");
    }

    Voucher voucher = voucherStorage.findById(voucherId);
    if (voucher == null) {
      throw new ResourceNotFoundException("Voucher " + voucherId + " is not found!");
    }

    voucher = modelMapper.toVoucher(dto);
    voucher.setId(voucherId);

    voucher.setExpiryDate(
        LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

    voucher.setState(
        dto.getState() != null
            ? dto.getState()
            : voucher.getState() != null ? voucher.getState() : VoucherState.INACTIVE);

    return voucherStorage.save(voucher);
  }

  public PageResponse<Voucher> listVoucher(
      String voucherId, String code, String name, Pageable pageable) {

    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    if (voucherId != null) {
      andConditions.add(Criteria.where("id").is(voucherId));
    }
    if (StringUtils.isNotEmpty(name)) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }
    if (StringUtils.isNotEmpty(code)) {
      andConditions.add(Criteria.where("code").regex(code, "i"));
    }

    Query query = new Query();
    Criteria andCriteria = new Criteria();

    query.addCriteria(andCriteria.andOperator(andConditions.toArray(new Criteria[0])));

    Page<Voucher> voucherPage = voucherStorage.findAll(query, pageable);

    //    PageResponse pageResponse = new PageResponse(voucherPage,)

    return PageResponse.createFrom(voucherPage);
  }

  public Voucher findById(Integer voucherId) {
    if (voucherId == null) {
      throw new BadRequestException("VocherId must be not null!");
    }

    Voucher voucher = voucherStorage.findById(voucherId);
    if (voucher == null) {
      throw new ResourceNotFoundException("Voucher " + voucherId + " not found!");
    }
    return voucher;
  }

  public PageResponse<Voucher> findByUserId(String userId, Pageable pageable) {
    if (userId == null) {
      throw new BadRequestException("UserId must be not null!");
    }

    Page<Voucher> voucherPage = voucherStorage.findByUserId(userId, pageable);
    if (voucherPage == null) {
      throw new ResourceNotFoundException("No voucher created by " + userId + " be found!");
    }
    return PageResponse.createFrom(voucherPage);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean delete(Integer voucherId) {

    Voucher voucher = voucherStorage.findById(voucherId);

    if (voucher == null) {
      throw new ResourceNotFoundException("no vouhcer fount!");
    }

    if (voucher.getExpiryDate().isAfter(LocalDateTime.now())) {
      throw new BadRequestException("Voucher is in expiry date");
    }

    return voucherStorage.delete(voucherId);
  }

  //  public static void main(String[] args) {
  //    System.out.println(LocalDateTime.now());
  //
  //    LocalDateTime localDateTime = LocalDateTime.of(2021, 11, 15, 15, 18);
  //    System.out.println(localDateTime.isAfter(LocalDateTime.now()));
  //  }

  //  public PageResponse<Voucher> findAll(Pageable pageable) {
  //    Page<Voucher> voucherPage = voucherStorage.findAll(pageable);
  //
  //    return PageResponse.createFrom(voucherPage);
  //  }

  public Boolean giveVoucher(GiveVoucherType type, List<Integer> accountIds, Integer voucherId) {

    Voucher voucher = voucherStorage.findById(voucherId);
    if (voucher == null) {
      throw new ResourceNotFoundException("No voucher fount!");
    }
    List<Account> accounts = new ArrayList<>();

    if (type == GiveVoucherType.ALL) {
      accounts = accountRepository.findAll();
    } else {
      if (accountIds == null) {
        throw new BadRequestException("List accountId null");
      }
      accounts = accountRepository.findAllById(accountIds);
    }

    List<VoucherAccount> voucherAccounts = new ArrayList<>();
    for (Account account : accounts) {
      VoucherAccount voucherAccount = new VoucherAccount(account.getId(), voucher);
      voucherAccount.setIsUsed(false);
      voucherAccounts.add(voucherAccount);

      voucherStorage.deleteCache();
    }
    voucherAccountRepository.saveAll(voucherAccounts);
    return true;
  }
}
