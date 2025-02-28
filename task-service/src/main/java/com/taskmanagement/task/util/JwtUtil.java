package com.taskmanagement.task.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final Key key;

  public JwtUtil(@Value("${jwt.secret}") String secretKey) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public UUID getUserIdFromToken(String token) {
    return UUID.fromString(
        Jwts.parser().setSigningKey(key).build().parseSignedClaims(token).getPayload()
            .getSubject());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
