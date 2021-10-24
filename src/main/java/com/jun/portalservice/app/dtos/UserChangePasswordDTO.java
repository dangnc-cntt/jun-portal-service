package com.jun.portalservice.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserChangePasswordDTO {

  @NotNull
  @Size(min = 6, max = 80)
  private String oldPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String newPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String confirmedPassword;
}
