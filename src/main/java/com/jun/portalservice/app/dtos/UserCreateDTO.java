package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.UserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserCreateDTO {
  @NotNull
  @Size(max = 50, min = 1)
  private String userName;

  @NotNull
  @Size(max = 50, min = 1)
  private String displayName;

  @NotNull
  @Size(max = 50, min = 6)
  private String password;

  private UserRole role;
}
