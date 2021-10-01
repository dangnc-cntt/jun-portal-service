package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.VoucherDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Voucher;
import com.jun.portalservice.domain.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "v1/portal/vouchers")
public class VoucherController {
  @Autowired private VoucherService voucherService;

  // TotalPage đang sai
  @GetMapping("listVoucher")
  public ResponseEntity<Page<Voucher>> listVoucher(
      @RequestParam(required = false) String voucherId,
      @RequestParam(required = false) String userId,
      Pageable pageable) {
    return ResponseEntity.ok(voucherService.listVoucher(voucherId, userId, pageable));
  }

  @PostMapping()
  public ResponseEntity<Voucher> create(
      @RequestBody @Valid VoucherDTO dto, @RequestHeader(name = "x-loyalty-user-id") String userId)
      throws Exception {
    return ResponseEntity.ok(voucherService.create(dto, userId));
  }

  @PutMapping(path = "{voucherId}")
  public ResponseEntity<Voucher> update(
      @RequestBody @Valid VoucherDTO dto,
      @PathVariable(name = "voucherId") String voucherId,
      @RequestHeader(name = "x-loyalty-user-id") String userId)
      throws Exception {
    return ResponseEntity.ok(voucherService.update(dto, voucherId, userId));
  }

  @GetMapping("{voucherId}")
  public ResponseEntity<Voucher> findById(@PathVariable(name = "voucherId") String voucherId) {
    return ResponseEntity.ok(voucherService.findById(voucherId));
  }

  @GetMapping()
  public ResponseEntity<PageResponse<Voucher>> findAll(Pageable pageable) {
    return ResponseEntity.ok(voucherService.findAll(pageable));
  }

  @GetMapping("user/{userId}")
  public ResponseEntity<PageResponse<Voucher>> findByUserId(
      @PathVariable(name = "userId") String userId, Pageable pageable) {
    return ResponseEntity.ok(voucherService.findByUserId(userId, pageable));
  }

  @DeleteMapping(path = "{voucherId}")
  public ResponseEntity<Boolean> delete(@PathVariable(name = "voucherId") String voucherId) {
    return ResponseEntity.ok(voucherService.delete(voucherId));
  }
}
