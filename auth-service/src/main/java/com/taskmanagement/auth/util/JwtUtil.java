package com.taskmanagement.auth.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final long jwtExpiration;
  private final Key key;

  public JwtUtil(@Value("${jwt.secret}") String secretKey,
                 @Value("${jwt.expiration}") long jwtExpiration) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.jwtExpiration = jwtExpiration;
  }

  public String generateAccessToken(UUID userId, String username) {
    return Jwts.builder()
        .setSubject(userId.toString())
        .claim("username", username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(key).build()
        .parseClaimsJws(token).getBody().getSubject();
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
