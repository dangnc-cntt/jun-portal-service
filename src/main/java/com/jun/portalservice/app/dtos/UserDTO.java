package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.Gender;
import com.jun.portalservice.domain.entities.types.UserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
  @NotNull
  @Size(max = 50, min = 1)
  private String username;

  @NotNull
  @Size(max = 50, min = 1)
  private String fullName;

  @NotNull
  @Size(max = 50, min = 6)
  private String password;

  @NotNull private String email;

  @NotNull private String phone;

  private String address;

  private String avatarUrl;

  @NotNull private Gender gender;

  @NotNull private UserRole role;
}
