package com.jun.portalservice.domain;

import com.jun.portalservice.app.dtos.*;
import com.jun.portalservice.app.responses.AccountResponse;
import com.jun.portalservice.app.responses.ProductResponse;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.entities.mongo.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

  TokenInfo toTokenInfo(User user);

  ProfileResponse toProfileResponse(User user);

  UserResponse toUserResponse(User user);

  AccountResponse toAccountResponse(Account account);

  Config toConfig(ConfigDTO dto);

  User toUser(UserDTO userDTO);

  User toUser(UpdateUserDTO userDTO);

  Category toCategory(CategoryDTO dto);

  Product toProduct(ProductDTO dto);

  ProductResponse toProductResponse(Product product);

  WarehouseExport toWarehouseExport(WarehouseDTO warehouseDTO);

  WarehouseReceipt toWarehouseReceipt(WarehouseDTO warehouseDTO);
}
