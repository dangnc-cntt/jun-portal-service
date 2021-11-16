package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.ColorDTO;
import com.jun.portalservice.app.dtos.ProductDTO;
import com.jun.portalservice.app.dtos.SizeDTO;
import com.jun.portalservice.app.responses.OptionResponse;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.ProductResponse;
import com.jun.portalservice.domain.entities.mongo.Color;
import com.jun.portalservice.domain.entities.mongo.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/portal/products")
public class ProductController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<ProductResponse>> filter(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Boolean isHot,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(productService.filter(categoryId, code, name, isHot, pageable));
  }

  @GetMapping("{productId}")
  public ResponseEntity<ProductResponse> findById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable int productId) {
    validateToken(token);
    return ResponseEntity.ok(productService.findById(productId));
  }

  @PostMapping()
  public ResponseEntity<ProductResponse> create(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestBody @Valid ProductDTO dto) {
    return ResponseEntity.ok(productService.create(dto, validateToken(token).getUserId()));
  }

  @PutMapping("{productId}")
  public ResponseEntity<ProductResponse> update(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestBody @Valid ProductDTO dto,
      @PathVariable int productId) {

    validateToken(token);
    return ResponseEntity.ok(productService.update(dto, productId));
  }

  @PostMapping(path = "color")
  public ResponseEntity<Color> createColor(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid ColorDTO dto) {
    validateToken(token);
    return ResponseEntity.ok(productService.createColor(dto));
  }

  @PostMapping(path = "size")
  public ResponseEntity<Size> createSize(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid SizeDTO dto) {
    validateToken(token);
    return ResponseEntity.ok(productService.createSize(dto));
  }

  @GetMapping(path = "size")
  public ResponseEntity<List<Size>> getAllSize(
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(productService.getAllSize());
  }

  @GetMapping(path = "color")
  public ResponseEntity<List<Color>> getAllColor(
      @RequestHeader(name = "x-jun-portal-token") String token) {
    validateToken(token);
    return ResponseEntity.ok(productService.getAllColor());
  }

  @GetMapping(path = "color/{colorId}")
  public ResponseEntity<Color> findColorById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable Integer colorId) {
    validateToken(token);
    return ResponseEntity.ok(productService.findColorById(colorId));
  }

  @GetMapping(path = "size/{sizeId}")
  public ResponseEntity<Size> findSizeById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable Integer sizeId) {
    validateToken(token);
    return ResponseEntity.ok(productService.findSizeById(sizeId));
  }

  @GetMapping(path = "option/{productId}")
  public ResponseEntity<List<OptionResponse>> findOptionByProductId(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable Integer productId) {
    validateToken(token);
    return ResponseEntity.ok(productService.findOptionByProductId(productId));
  }
}
