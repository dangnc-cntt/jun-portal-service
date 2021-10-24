package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateUserDTO {
  @NotNull
  @Size(max = 50, min = 1)
  private String username;

  @NotNull
  @Size(max = 50, min = 1)
  private String fullName;

  @NotNull private String email;

  @NotNull private String phone;

  private String address;

  private String avatarUrl;

  @NotNull private Gender gender;
}
