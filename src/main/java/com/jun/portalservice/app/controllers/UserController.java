package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.UserDTO;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.services.AuthService;
import com.jun.portalservice.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/users")
public class UserController extends BaseController {
  @Autowired private UserService userService;
  @Autowired private AuthService authService;

  @GetMapping("profile")
  public ResponseEntity<ProfileResponse> getProfile(
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(userService.getProfile(authService.validateToken(token).getUserId()));
  }

  @GetMapping()
  public ResponseEntity<?> test() {
    return ResponseEntity.ok("ok");
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUserById(
      @PathVariable String id, @Valid @RequestHeader(name = "x-jun-portal-token") String token)
      throws Exception {
    return ResponseEntity.ok(userService.getById(id));
  }

  @PostMapping()
  public ResponseEntity<Boolean> create(
      @Valid @RequestBody UserDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(
        userService.create(userDTO, authService.validateToken(token).getUserRole()));
  }

  @PutMapping("{id}")
  public ResponseEntity<String> update(
      @PathVariable String id,
      @Valid @RequestBody UserDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(
        userService.update(id, userDTO, authService.validateToken(token).getUserRole()));
  }

  @DeleteMapping("{id}")
  public String delete(
      @PathVariable String id, @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return userService.delete(id, authService.validateToken(token).getUserRole());
  }
}
