package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.UserDTO;
import com.jun.portalservice.app.responses.ProfileResponse;
import com.jun.portalservice.app.responses.UserResponse;
import com.jun.portalservice.domain.entities.mongo.User;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import com.jun.portalservice.domain.exceptions.AccountAlreadyExistsException;
import com.jun.portalservice.domain.exceptions.AccountNotExistsException;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService extends BaseService {

  public ProfileResponse getProfile(int userId) {

    User user = userRepository.findUserById(userId);

    if (user == null) {
      throw new AccountNotExistsException();
    }

    ProfileResponse response = modelMapper.toProfileResponse(user);
    return response;
  }

  public UserResponse getById(String id) throws Exception {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      new Exception("Not found with id " + id);
    }
    return modelMapper.toUserResponse(user);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean create(UserDTO userDTO, UserRole role) {

    if (!role.equals(UserRole.ADMIN)) {
      throw new BadRequestException("User must be ADMIN!");
    }

    if (userDTO.getRole().equals(UserRole.ADMIN)) {
      throw new BadRequestException("This account is not ADMIN!");
    }

    User user = userRepository.findUserByUsername(userDTO.getUsername());
    if (user != null) {
      throw new AccountAlreadyExistsException();
    }
    user = new User();
    user = modelMapper.toUser(userDTO);
    user.setId((int) generateSequence(User.SEQUENCE_NAME));
    user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12)));
    user.setState(UserState.ACTIVATED);
    userRepository.save(user);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public String update(String id, UserDTO userDTO, UserRole role) {
    if (!role.equals(UserRole.ADMIN)) {
      throw new ResourceNotFoundException("====Access rights: ADMIN");
    }
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      return "Not found with id " + id;
    } else {
      user = modelMapper.toUser(userDTO);
      userRepository.save(user);
      return "Update Successfully!";
    }
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public String delete(String id, UserRole role) {
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
