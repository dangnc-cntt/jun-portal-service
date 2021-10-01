package com.jun.portalservice.app.controllers;

import com.jun.portalservice.app.responses.AccountResponse;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.types.AccountState;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/ ")
public class AccountController extends BaseController {

  @Autowired private AccountService accountService;

  @GetMapping()
  public ResponseEntity<PageResponse<AccountResponse>> findAll(
      @RequestHeader(name = "x-jun-portal-token") String token, Pageable pageable) {
    validateToken(token);
    return ResponseEntity.ok(accountService.findAll(pageable));
  }

  @PutMapping("{accountId}/ban")
  public ResponseEntity<AccountResponse> banAccount(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable("accountId") String accountId) {
    UserRole userRole = validateToken(token).getUserRole();
    return ResponseEntity.ok(
        accountService.updateStateUser(accountId, AccountState.BANNED, userRole));
  }

  @PutMapping("{accountId}/reactive")
  public ResponseEntity<AccountResponse> reactiveAccount(
      @RequestHeader(name = "x-jun-portal-token") String token,
      @PathVariable("accountId") String accountId) {
    UserRole userRole = validateToken(token).getUserRole();
    return ResponseEntity.ok(
        accountService.updateStateUser(accountId, AccountState.ACTIVATED, userRole));
  }
}
