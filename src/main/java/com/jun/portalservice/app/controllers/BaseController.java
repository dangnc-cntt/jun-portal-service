package com.jun.portalservice.app.controllers;

import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private AuthService authService;

  public TokenInfo validateToken(String token) {
    return authService.validateToken(token);
  }
}
