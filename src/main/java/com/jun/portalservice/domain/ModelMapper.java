package com.jun.portalservice.domain;

import com.jun.portalservice.app.dtos.CategoryDTO;
import com.jun.portalservice.app.dtos.ConfigDTO;
import com.jun.portalservice.app.dtos.UserCreateDTO;
import com.jun.portalservice.app.dtos.UserDTO;
import com.jun.portalservice.app.responses.AccountResponse;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.entities.mongo.Account;
import com.jun.portalservice.domain.entities.mongo.Category;
import com.jun.portalservice.domain.entities.mongo.Config;
import com.jun.portalservice.domain.entities.mongo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

  TokenInfo toTokenInfo(User user);

  ProfileResponse toProfileResponse(User user);

  User toUser(UserCreateDTO dto);

  UserResponse toUserResponse(User user);

  AccountResponse toAccountResponse(Account account);

  Config toConfig(ConfigDTO dto);

  User toUser(UserDTO userDTO);

  Category toCategory(CategoryDTO dto);
}
