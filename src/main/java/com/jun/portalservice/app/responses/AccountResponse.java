package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.types.AccountState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponse {
  private String id;

  private String fullName;

  private String userName;

  private String avatarUrl;

  private AccountState state;
}
