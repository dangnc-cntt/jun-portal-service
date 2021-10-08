package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.domain.entities.types.AccountState;
import com.jun.portalservice.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "accounts")
@NoArgsConstructor
public class Account extends BaseEntity {

  @Id private Integer id;

  @Field(name = "username")
  private String username;

  @Field(name = "password")
  private String password;

  @Field(name = "full_name")
  private String fullName;

  @Field(name = "phone")
  private String phoneNumber;

  @Field(name = "email")
  private String email;

  @Field(name = "confirmed_at")
  private LocalDateTime confirmedAt;

  @Field(name = "address")
  private LocalDateTime address;

  @Field(name = "gender")
  private Gender gender;

  @Field(name = "avatar_url")
  private String avatarUrl;

  @Field(name = "state")
  private AccountState state;
}
