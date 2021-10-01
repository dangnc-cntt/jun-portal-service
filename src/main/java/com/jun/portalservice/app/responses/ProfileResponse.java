package com.jun.portalservice.app.responses;

import com.jun.portalservice.domain.entities.types.Gender;
import lombok.Data;

@Data
public class ProfileResponse {
  private String id;

  private String username;

  private String fullName;

  private String email;

  private String phone;

  private String address;

  private String avatarUrl;

  private Gender gender;
}
