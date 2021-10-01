package com.jun.portalservice.domain.data;

import com.jun.portalservice.domain.entities.mongo.User;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenInfo {
  private String id;
  private Integer userId;
  private UserRole userRole;
  private UserState userState;

  public TokenInfo(User user) {
    setUserId(user.getId());
    setId(user.getId() + "");
    setUserRole(user.getRole());
    setUserState(user.getState());
  }
}
