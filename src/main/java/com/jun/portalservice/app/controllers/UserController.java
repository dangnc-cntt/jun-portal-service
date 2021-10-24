package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.UpdateUserDTO;
import com.jun.portalservice.app.dtos.UserChangePasswordDTO;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/users")
public class UserController extends BaseController {

  @GetMapping("profile")
  public ResponseEntity<ProfileResponse> getProfile(
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(userService.getProfile(validateToken(token).getUserId()));
  }

  @PutMapping()
  public ResponseEntity<UserResponse> update(
      @Valid @RequestBody UpdateUserDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(userService.update(userDTO, validateToken(token).getUserId()));
  }

  @PutMapping("change_password")
  public ResponseEntity<UserResponse> changePass(
      @Valid @RequestBody UserChangePasswordDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(userService.changePassword(userDTO, validateToken(token).getUserId()));
  }
}
