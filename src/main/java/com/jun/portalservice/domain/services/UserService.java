package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.UpdateUserDTO;
import com.jun.portalservice.app.dtos.UserChangePasswordDTO;
import com.jun.portalservice.app.dtos.UserDTO;
import com.jun.portalservice.app.responses.Metadata;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.entities.mongo.User;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import com.jun.portalservice.domain.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserService extends BaseService {

  public PageResponse<UserResponse> filter(
      UserRole userRole, String fullName, Integer userId, Pageable pageable)
      throws AuthenticationException {
    if (userRole != UserRole.ADMIN) {
      throw new AuthenticationException();
    }
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));
    if (StringUtils.isNotEmpty(fullName)) {
      andConditions.add(Criteria.where("fullName").regex(fullName, "i"));
    }
    if (userId != null) {
      andConditions.add(Criteria.where("id").is(userId));
    }
    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));

    Page<User> userPage = userRepository.findAll(query, pageable);
    List<UserResponse> responseList = new ArrayList<>();
    if (userPage != null && userPage.getContent().size() > 0) {
      for (User user : userPage.getContent()) {
        UserResponse userResponse = modelMapper.toUserResponse(user);
        responseList.add(userResponse);
      }
    }
    return new PageResponse<>(responseList, Metadata.createFrom(userPage));
  }

  public ProfileResponse getProfile(int userId) {

    User user = userRepository.findUserById(userId);

    if (user == null) {
      throw new AccountNotExistsException();
    }

    ProfileResponse response = modelMapper.toProfileResponse(user);
    return response;
  }

  public UserResponse getById(int id) throws Exception {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new Exception("Not found with id " + id);
    }
    return modelMapper.toUserResponse(user);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public UserResponse create(UserDTO userDTO, UserRole role) throws AuthenticationException {

    if (!role.equals(UserRole.ADMIN)) {
      throw new AuthenticationException();
    }

    User user = userRepository.findUserByUsername(userDTO.getUsername());
    if (user != null) {
      throw new AccountAlreadyExistsException();
    }
    user = new User();
    user = modelMapper.toUser(userDTO);
    user.setId((int) generateSequence(User.SEQUENCE_NAME));
    user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12)));
    user.setRole(userDTO.getRole());
    user.setState(UserState.ACTIVATED);
    user = userRepository.save(user);
    return modelMapper.toUserResponse(user);
  }

  public UserResponse updateState(UserState state, int userId, UserRole role)
      throws AuthenticationException {
    if (role != UserRole.ADMIN) {
      throw new AuthenticationException("Access rights: ADMIN");
    }
    User user = userRepository.findUserById(userId);
    if (user != null) {
      throw new ResourceNotFoundException("User " + userId + " not found");
    }

    user.setState(state);
    user = userRepository.save(user);
    return modelMapper.toUserResponse(user);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public UserResponse updateForAdmin(Integer id, UpdateUserDTO userDTO, UserRole role) {
    if (!role.equals(UserRole.ADMIN)) {
      throw new ResourceNotFoundException("====Access rights: ADMIN");
    }
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }
    user.from(userDTO);
    userRepository.save(user);
    return modelMapper.toUserResponse(user);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public UserResponse update(UpdateUserDTO userDTO, int userId) {

    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }
    user.from(userDTO);
    userRepository.save(user);
    return modelMapper.toUserResponse(user);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public UserResponse changePassword(UserChangePasswordDTO userDTO, int userId) {

    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }
    comparePassword(userDTO, user.getPassword());
    String password = BCrypt.hashpw(userDTO.getNewPassword(), BCrypt.gensalt(12));
    user.setPassword(password);
    user.setId(userId);
    userRepository.save(user);
    return modelMapper.toUserResponse(user);
  }

  protected void comparePassword(UserChangePasswordDTO dto, String password) {
    boolean checkOldPassword = BCrypt.checkpw(dto.getOldPassword(), password);
    if (!checkOldPassword) {
      throw new BadRequestException("Wrong old password!");
    }
    comparePassword(dto.getNewPassword(), dto.getConfirmedPassword());
  }

  protected void comparePassword(String password, String confirmedPassword) {
    if (!password.equals(confirmedPassword)) {
      throw new AccountRetypePasswordNotMatchException();
    }
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public String delete(Integer id, UserRole role) {
    if (!role.equals(UserRole.ADMIN)) {
      throw new ResourceNotFoundException("====Access rights: ADMIN");
    }
    return userRepository
        .findById(id)
        .map(
            user -> {
              userRepository.delete(user);
              return "Delete Successfully!";
            })
        .orElse("User not found with id " + id);
  }
}
