package com.taskmanagement.auth.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String SECRET_KEY = "mySuperSecretKey1234567890mySuperSecretKey";
  private static final long EXPIRATION_TIME = 15 * 60 * 1000;

  private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

  public String generateAccessToken(UUID userId, String username) {
    return Jwts.builder()
        .setSubject(userId.toString())
        .claim("username", username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public UUID getUserIdFromToken(String token) {
    return UUID.fromString(Jwts.parser().setSigningKey(key).build()
        .parseClaimsJws(token).getBody().getSubject());
  }

}
