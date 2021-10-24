package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.app.dtos.UpdateUserDTO;
import com.jun.portalservice.domain.entities.types.Gender;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "user")
@NoArgsConstructor
public class User extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "user_sequence";
  @Id private int id;

  @Field(name = "user_name")
  private String username;

  @Field(name = "full_name")
  private String fullName;

  @Field(name = "password")
  private String password;

  @Field(name = "state")
  private UserState state;

  @Field(name = "role")
  private UserRole role;

  @Field(name = "email")
  private String email;

  @Field(name = "phone")
  private String phone;

  @Field(name = "address")
  private String address;

  @Field(name = "avatar_url")
  private String avatarUrl;

  @Field(name = "gender")
  private Gender gender;

  public void from(UpdateUserDTO userDTO) {
    setUsername(userDTO.getUsername());
    setFullName(userDTO.getFullName());
    setEmail(userDTO.getEmail());
    setPhone(userDTO.getPhone());
    setAddress(userDTO.getAddress());
    setAvatarUrl(userDTO.getAvatarUrl());
    setGender(userDTO.getGender());
  }
}
