package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.entities.types.ConfigType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/config")
public class ConfigController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<Config>> fillter(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam(required = false) String key,
      @RequestParam(required = false) ConfigType type,
      Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(configService.list(key, type, pageable));
  }

  @GetMapping("{configKey}")
  public ResponseEntity<Config> fillter(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable("configKey") String configKey) {
    validateToken(token);
    return ResponseEntity.ok(configService.detail(configKey));
  }

  @PostMapping()
  public ResponseEntity<Config> fillter(
      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid ConfigDTO dto) {
    validateToken(token);
    return ResponseEntity.ok(configService.create(dto));
  }

  @DeleteMapping("{configKey}")
  public ResponseEntity<Boolean> delete(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable("configKey") String configKey) {
    validateToken(token);
    return ResponseEntity.ok(configService.delete(configKey));
  }
}
