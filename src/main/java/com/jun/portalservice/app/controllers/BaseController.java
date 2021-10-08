package com.jun.portalservice.app.controllers;

import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.services.AuthService;
import com.jun.portalservice.domain.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private AuthService authService;
  @Autowired protected CategoryService categoryService;

  public TokenInfo validateToken(String token) {
    return authService.validateToken(token);
  }
}
