package com.jun.portalservice.domain;

import com.jun.portalservice.app.dtos.*;
import com.jun.portalservice.app.responses.*;
import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.entities.mongo.*;
import org.mapstruct.Mapper;

import java.util.List;

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

  List<OptionResponse> toOptionResponse(List<ProductOption> productOption);

  Voucher toVoucher(VoucherDTO dto);
}
