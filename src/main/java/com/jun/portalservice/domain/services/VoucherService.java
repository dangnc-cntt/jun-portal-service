package com.jun.portalservice.domain.services;

import com.jun.portalservice.domain.entities.types.VoucherState;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import com.jun.portalservice.app.dtos.VoucherDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService extends BaseService {

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Voucher create(VoucherDTO dto, String userId) throws Exception {
    List<Voucher> voucherList = new ArrayList<>();

    for (int i = 0; i < 30; i++) {
      if (dto == null) {
        throw new BadRequestException("RequestBody is null!");
      }
      if (userId == null) {
        throw new BadRequestException("UserId is null!");
      }

      Voucher voucher = new Voucher();
      voucher.from(dto);
      voucher.setCreatedBy(userId);
      voucher.setState(VoucherState.ACTIVE);
      voucherList.add(voucher);
      voucherStorage.save(voucher);
    }
    return voucherList.get(1);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Voucher update(VoucherDTO dto, String voucherId, String userId) throws Exception {
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

    voucher.from(dto);
    if (voucher.getState() != VoucherState.ACTIVE
        && dto.getState() != null
        && dto.getState() == VoucherState.ACTIVE) {
      voucher.setActivatedAt(LocalDateTime.now());
    }

    voucher.setState(
        dto.getState() != null
            ? dto.getState()
            : voucher.getState() != null ? voucher.getState() : VoucherState.INACTIVE);
    voucher.setUpdatedBy(userId);

    return voucherStorage.save(voucher);
  }

  public Page<Voucher> listVoucher(String voucherId, String userId, Pageable pageable) {

    List<Criteria> andConditions = new ArrayList<>();

    if (voucherId != null) {
      andConditions.add(Criteria.where("id").is(voucherId));
    }
    if (userId != null) {
      andConditions.add(Criteria.where("created_by").is(userId));
    }

    if (andConditions == null || andConditions.size() == 0) {
      andConditions.add(Criteria.where("id").ne(null));
    }
    Query query = new Query();
    Criteria andCriteria = new Criteria();

    query.addCriteria(
        andCriteria.andOperator(andConditions.toArray(new Criteria[andConditions.size()])));

    Page<Voucher> voucherPage = voucherStorage.findAll(query, pageable);

    //    PageResponse pageResponse = new PageResponse(voucherPage,)

    return voucherPage;
  }

  public Voucher findById(String voucherId) {
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
  public boolean delete(String voucherId) {
    return voucherStorage.delete(voucherId);
  }

  public PageResponse<Voucher> findAll(Pageable pageable) {
    Page<Voucher> voucherPage = voucherStorage.findAll(pageable);

    return PageResponse.createFrom(voucherPage);
  }
}
