package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
  private String id;
  private String userName;
  private String displayName;
  private UserRole role;
  private UserState state;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
