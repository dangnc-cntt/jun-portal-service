package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.dtos.UpdateUserDTO;
import com.jun.portalservice.app.dtos.UserDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.entities.types.UserState;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/portal/admin/users")
public class AdminController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<UserResponse>> filter(
      @Valid @RequestHeader(name = "x-jun-portal-token") String token,
      @RequestParam(required = false) String fullName,
      @RequestParam(required = false) Integer userId,
      Pageable pageable)
      throws AuthenticationException {
    return ResponseEntity.ok(
        userService.filter(validateToken(token).getUserRole(), fullName, userId, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUserById(
      @PathVariable Integer id, @Valid @RequestHeader(name = "x-jun-portal-token") String token)
      throws Exception {
    validateToken(token);
    return ResponseEntity.ok(userService.getById(id));
  }

  @PostMapping()
  public ResponseEntity<UserResponse> create(
      @Valid @RequestBody UserDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token)
      throws AuthenticationException {
    return ResponseEntity.ok(userService.create(userDTO, validateToken(token).getUserRole()));
  }

  @PutMapping("{id}")
  public ResponseEntity<UserResponse> update(
      @PathVariable Integer id,
      @Valid @RequestBody UpdateUserDTO userDTO,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return ResponseEntity.ok(
        userService.updateForAdmin(id, userDTO, validateToken(token).getUserRole()));
  }

  @PutMapping("state/{id}")
  public ResponseEntity<UserResponse> updateState(
      @PathVariable Integer id,
      @RequestParam UserState state,
      @Valid @RequestHeader(name = "x-jun-portal-token") String token)
      throws AuthenticationException {
    return ResponseEntity.ok(
        userService.updateState(state, id, validateToken(token).getUserRole()));
  }

  @DeleteMapping("{id}")
  public String delete(
      @PathVariable Integer id, @Valid @RequestHeader(name = "x-jun-portal-token") String token) {
    return userService.delete(id, validateToken(token).getUserRole());
  }
}
