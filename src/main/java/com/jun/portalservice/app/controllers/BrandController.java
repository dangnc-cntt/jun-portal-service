package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.BrandDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/brands")
public class BrandController extends BaseController {
  @GetMapping()
  public ResponseEntity<PageResponse<Brand>> filter(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer id,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(brandService.filter(name, id, pageable));
  }

  @GetMapping("{brandId}")
  public ResponseEntity<Brand> findById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable() int brandId) {
    validateToken(token);
    return ResponseEntity.ok(brandService.findById(brandId));
  }

  @PostMapping()
  public ResponseEntity<Brand> create(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid BrandDTO dto) {
    validateToken(token);
    return ResponseEntity.ok(brandService.create(dto));
  }

  @PutMapping("{brandId}")
  public ResponseEntity<Brand> update(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable() int brandId,
      @RequestBody @Valid BrandDTO dto) {
    validateToken(token);
    return ResponseEntity.ok(brandService.update(dto, brandId));
  }
}
