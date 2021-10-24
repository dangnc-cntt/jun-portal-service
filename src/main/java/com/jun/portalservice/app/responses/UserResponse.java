package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.types.Gender;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
  private String id;

  private String username;

  private String fullName;

  private UserState state;

  private UserRole role;

  private String email;

  private String phone;

  private String address;

  private String avatarUrl;

  private Gender gender;

  protected LocalDateTime createdAt;

  protected LocalDateTime updatedAt;
}
