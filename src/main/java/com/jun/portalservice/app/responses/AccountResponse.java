package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.types.AccountState;
import com.jun.portalservice.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountResponse {
  @Id private Integer id;

  private String username;

  private String fullName;

  private String phoneNumber;

  private String email;

  private LocalDateTime confirmedAt;

  private String address;

  private Gender gender;

  private String avatarUrl;

  private AccountState state;
}
