package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.WarehouseDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.WarehouseExport;
import com.jun.portalservice.domain.entities.mongo.WarehouseReceipt;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/warehouse")
public class WarehouseController extends BaseController {
  @GetMapping("receipts")
  public ResponseEntity<PageResponse<WarehouseReceipt>> fillterReceipt(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String gte,
      @RequestParam(required = false) String lte,
      @RequestHeader(name = "x-jun-portal-token") String token,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(warehouseReceiptService.fillter(id, gte, lte, pageable));
  }

  @GetMapping("receipts/{receiptId}")
  public ResponseEntity<WarehouseReceipt> findReceiptById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable Integer receiptId) {
    validateToken(token);
    return ResponseEntity.ok(warehouseReceiptService.findById(receiptId));
  }

  @PostMapping("receipts")
  public ResponseEntity<WarehouseReceipt> createReceipt(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody WarehouseDTO dto) {
    return ResponseEntity.ok(warehouseReceiptService.create(dto, validateToken(token).getUserId()));
  }

  @PostMapping("exports")
  public ResponseEntity<WarehouseExport> createExport(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody WarehouseDTO dto) {
    return ResponseEntity.ok(warehouseExportService.create(dto, validateToken(token).getUserId()));
  }

  @GetMapping("exports/{exportId}")
  public ResponseEntity<WarehouseExport> findExportById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable Integer exportId) {
    validateToken(token);
    return ResponseEntity.ok(warehouseExportService.findById(exportId));
  }

  @GetMapping("exports")
  public ResponseEntity<PageResponse<WarehouseExport>> fillterExport(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String gte,
      @RequestParam(required = false) String lte,
      @RequestHeader(name = "x-jun-portal-token") String token,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(warehouseExportService.fillter(id, gte, lte, pageable));
  }
}
