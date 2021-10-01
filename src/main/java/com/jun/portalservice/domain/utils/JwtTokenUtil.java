package com.jun.portalservice.domain.utils;

import com.jun.portalservice.domain.data.TokenInfo;
import com.jun.portalservice.domain.entities.types.UserRole;
import com.jun.portalservice.domain.entities.types.UserState;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
  private static final long serialVersionUID = -2550185165626007488L;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.token-expire-time}")
  private int tokenExpireTime;

  // generate token for user
  public String generateToken(TokenInfo tokenInfo) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", tokenInfo.getUserId());
    claims.put("userRole", tokenInfo.getUserRole().toString());
    claims.put("userState", tokenInfo.getUserState().toString());

    return doGenerateToken(claims, tokenInfo.getId().toString());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public TokenInfo validateToken(String accessToken) {
    Claims claims = getAllClaimsFromToken(accessToken);

    Date expiration = claims.getExpiration();
    if (expiration.before(new Date())) {
      return null;
    }

    TokenInfo tokenInfo = new TokenInfo();
    tokenInfo.setId(claims.get(Claims.SUBJECT, String.class));
    tokenInfo.setUserId(claims.get("userId", Integer.class));
    tokenInfo.setUserRole(UserRole.valueOf(claims.get("userRole", String.class)));
    tokenInfo.setUserState(UserState.valueOf(claims.get("userState", String.class)));

    return tokenInfo;
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }
}
