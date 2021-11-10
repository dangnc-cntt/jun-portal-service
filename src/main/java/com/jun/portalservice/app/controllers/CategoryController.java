package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.CategoryDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Category;
import com.jun.portalservice.domain.entities.types.CategoryState;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/categories")
public class CategoryController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<Category>> filter(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false) CategoryState state,
      @RequestParam(required = false) String name,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(categoryService.filter(categoryId, state, name, pageable));
  }

  @GetMapping("{categoryId}")
  public ResponseEntity<Category> findById(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable int categoryId) {
    validateToken(token);
    return ResponseEntity.ok(categoryService.findById(categoryId));
  }

  @PostMapping()
  public ResponseEntity<Category> create(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestBody @Valid CategoryDTO dto) {
    return ResponseEntity.ok(categoryService.create(dto, validateToken(token).getUserId()));
  }

  @PutMapping(path = "{categoryId}")
  public ResponseEntity<Category> update(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestBody @Valid CategoryDTO dto,
      @PathVariable int categoryId) {
    validateToken(token);
    return ResponseEntity.ok(categoryService.update(dto, categoryId));
  }

  @DeleteMapping(path = "{categoryId}")
  public ResponseEntity<Boolean> delete(
      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable int categoryId) {
    validateToken(token);
    return ResponseEntity.ok(categoryService.delete(categoryId));
  }
}
