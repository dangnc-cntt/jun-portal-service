package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.custom_annotations.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
  @NotNull @UsernameConstraint private String username;

  @NotNull private String password;
}
