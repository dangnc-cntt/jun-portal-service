package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.LoginDTO;
import com.jun.portalservice.app.dtos.RefreshTokenDTO;
import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.entities.mongo.User;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import com.jun.portalservice.domain.exceptions.*;
import com.jun.portalservice.domain.utils.Caching;
import com.jun.portalservice.domain.utils.JwtRefreshTokenUtil;
import com.jun.portalservice.domain.utils.JwtTokenUtil;
import com.jun.portalservice.domain.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class AuthService extends BaseService {

  @Autowired protected JwtTokenUtil jwtTokenUtil;
  @Autowired private Caching caching;
  @Autowired protected JwtRefreshTokenUtil jwtRefreshTokenUtil;

  public Map<String, String> login(LoginDTO dto) throws Exception {
    User user = userRepository.findUserByUsername(dto.getUsername());
    log.info(user);

    if (user == null) {
      throw new AccountNotExistsException();
    }

    boolean checkPassword = BCrypt.checkpw(dto.getPassword(), user.getPassword());
    if (checkPassword) {
      if (user.getState() != (UserState.ACTIVATED)) {
        throw new BadRequestException(Message.ACCOUNT_NOT_ACTIVATED);
      }
    } else {
      throw new WrongAccountOrPasswordException();
    }

    TokenInfo tokenInfo = new TokenInfo(user);
    log.info(tokenInfo);
    Map<String, String> response = new HashMap<>();
    response.put("accessToken", jwtTokenUtil.generateToken(tokenInfo));
    response.put("refreshToken", jwtRefreshTokenUtil.generateToken(tokenInfo));
    //    response.put("userRole", user.getRole().toString());
    log.info(response);

    return response;
  }

  public boolean logout(RefreshTokenDTO refreshToken) {
    String refreshTokenValue = refreshToken.getRefreshToken();
    caching.del(refreshTokenValue);
    return true;
  }

  public Map<String, String> refreshToken(RefreshTokenDTO refreshTokenDTO) {
    TokenInfo tokenInfo = jwtRefreshTokenUtil.validate(refreshTokenDTO.getRefreshToken());
    if (tokenInfo == null) {
      throw new UnauthorizedException();
    }

    Map<String, String> response = new HashMap<>();
    response.put("refreshToken", jwtTokenUtil.generateToken(tokenInfo));

    return response;
  }

  public TokenInfo validateToken(String accessToken) {
    TokenInfo tokenInfo = jwtTokenUtil.validateToken(accessToken);
    if (tokenInfo == null) {
      throw new AuthenticationCodeNotExistsException();
    }
    return tokenInfo;
  }

  public void verifyToken(String token) {

    TokenInfo tokenInfo = jwtTokenUtil.validateToken(token);
    if (tokenInfo == null) {
      throw new ResourceNotFoundException("===========Not found tokenInfo" + tokenInfo);
    }
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void initUser() {
    User user = userRepository.findUserByUsername("admin");
    if (user != null) {
      throw new AccountAlreadyExistsException();
    }

    user = new User();
    user.setId((int) generateSequence(User.SEQUENCE_NAME));
    user.setUsername("admin");
    user.setFullName("Administrator");
    user.setRole(UserRole.ADMIN);
    user.setState(UserState.ACTIVATED);
    user.setState(UserState.ACTIVATED);
    user.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt(12)));
    userRepository.save(user);
  }
}
