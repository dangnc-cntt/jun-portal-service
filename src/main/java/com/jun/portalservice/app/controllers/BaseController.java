package com.jun.portalservice.app.controllers;

import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired protected CategoryService categoryService;
  @Autowired protected UserService userService;
  @Autowired protected AuthService authService;
  @Autowired protected ProductService productService;
  @Autowired protected WarehouseReceiptService warehouseReceiptService;
  @Autowired protected WarehouseExportService warehouseExportService;

  public TokenInfo validateToken(String token) {
    return authService.validateToken(token);
  }
}
