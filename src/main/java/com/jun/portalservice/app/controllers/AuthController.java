package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.LoginDTO;
import com.jun.portalservice.app.dtos.RefreshTokenDTO;
import com.jun.portalservice.domain.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "v1/portal/auth")
@Slf4j
public class AuthController {
  @Autowired private AuthService authService;

  @PostMapping(path = "login")
  public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO dto)
      throws Exception {
    return ResponseEntity.ok(authService.login(dto));
  }

  @PostMapping("logout")
  public ResponseEntity<Boolean> logout(@RequestBody RefreshTokenDTO refreshToken) {
    return ResponseEntity.ok(authService.logout(refreshToken));
  }

  @GetMapping(path = "init")
  public ResponseEntity<Boolean> init() {
    authService.initUser();
    return ResponseEntity.ok(true);
  }

  @PostMapping("refreshToken")
  public ResponseEntity<Map<String, String>> refreshToken(@RequestBody @Valid RefreshTokenDTO dto) {
    return ResponseEntity.ok(authService.refreshToken(dto));
  }
}
