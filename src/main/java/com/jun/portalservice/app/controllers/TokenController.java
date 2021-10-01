package com.jun.portalservice.app.controllers;

import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "v1/portal/tokens")
@Slf4j
public class TokenController {
  @Autowired private AuthService authService;

  @PostMapping(path = "validate")
  public ResponseEntity<TokenInfo> validateToken(
      @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(authService.validateToken(token));
  }
}
