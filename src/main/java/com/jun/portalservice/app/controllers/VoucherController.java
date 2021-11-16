package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.VoucherDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Voucher;
import com.jun.portalservice.domain.entities.types.GiveVoucherType;
import com.jun.portalservice.domain.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "v1/portal/vouchers")
public class VoucherController extends BaseController {
  @Autowired private VoucherService voucherService;

  @GetMapping()
  public ResponseEntity<PageResponse<Voucher>> listVoucher(
      @RequestParam(required = false) String voucherId,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name,
      @RequestHeader(name = "x-jun-portal-token") String token,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(voucherService.listVoucher(voucherId, code, name, pageable));
  }

  @PostMapping()
  public ResponseEntity<Voucher> create(
      @RequestBody @Valid VoucherDTO dto, @RequestHeader(name = "x-jun-portal-token") String token)
      throws Exception {
    return ResponseEntity.ok(voucherService.create(dto, validateToken(token).getUserId()));
  }

  @PutMapping(path = "{voucherId}")
  public ResponseEntity<Voucher> update(
      @RequestBody @Valid VoucherDTO dto,
      @PathVariable(name = "voucherId") Integer voucherId,
      @RequestHeader(name = "x-jun-portal-token") String token)
      throws Exception {
    return ResponseEntity.ok(
        voucherService.update(dto, voucherId, validateToken(token).getUserId()));
  }

  @GetMapping("{voucherId}")
  public ResponseEntity<Voucher> findById(@PathVariable(name = "voucherId") Integer voucherId) {
    return ResponseEntity.ok(voucherService.findById(voucherId));
  }
  //
  //  @GetMapping()
  //  public ResponseEntity<PageResponse<Voucher>> findAll(Pageable pageable) {
  //    return ResponseEntity.ok(voucherService.findAll(pageable));
  //  }

  @DeleteMapping(path = "{voucherId}")
  public ResponseEntity<Boolean> delete(@PathVariable(name = "voucherId") Integer voucherId) {
    return ResponseEntity.ok(voucherService.delete(voucherId));
  }

  @PostMapping(path = "give/{voucherId}")
  public ResponseEntity<Boolean> giveVoucher(
      @PathVariable(name = "voucherId") Integer voucherId,
      @RequestBody @Valid List<Integer> accountIds,
      @RequestParam("type") GiveVoucherType type) {
    return ResponseEntity.ok(voucherService.giveVoucher(type, accountIds, voucherId));
  }
}
