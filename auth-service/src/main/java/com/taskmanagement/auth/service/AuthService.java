package com.taskmanagement.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.auth.entity.RefreshTokenEntity;
import com.taskmanagement.auth.entity.RoleEntity;
import com.taskmanagement.auth.entity.UserEntity;
import com.taskmanagement.auth.entity.UserRoleEntity;
import com.taskmanagement.auth.entity.UserRoleId;
import com.taskmanagement.auth.kafka.producer.KafkaProducer;
import com.taskmanagement.auth.repository.RefreshTokenRepository;
import com.taskmanagement.auth.repository.RoleRepository;
import com.taskmanagement.auth.repository.UserRepository;
import com.taskmanagement.auth.service.exception.InvalidPasswordException;
import com.taskmanagement.auth.service.exception.InvalidRefreshTokenException;
import com.taskmanagement.auth.service.exception.NotFoundRefreshTokenException;
import com.taskmanagement.auth.service.exception.NotFoundRoleException;
import com.taskmanagement.auth.service.exception.NotFoundUserException;
import com.taskmanagement.auth.service.exception.RefreshTokenExpiredException;
import com.taskmanagement.auth.service.model.CreatedRefreshToken;
import com.taskmanagement.auth.service.model.GetUser;
import com.taskmanagement.auth.service.model.NewUser;
import com.taskmanagement.auth.service.model.Role;
import com.taskmanagement.auth.service.model.User;
import com.taskmanagement.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final KafkaProducer kafkaProducer;
  private final ObjectMapper objectMapper;
  private final JwtUtil jwtUtil;

  @Transactional
  public User registerUser(NewUser newUser) {
    UserEntity userEntity = new UserEntity();

    userEntity.setUsername(newUser.username());
    userEntity.setPassword(passwordEncoder.encode(newUser.password()));
    userEntity.setCreatedAt(LocalDate.now());

    RoleEntity userRole = roleRepository.findByName("ROLE_USER")
        .orElseThrow(() -> new NotFoundRoleException("Default role not found"));

    UserRoleEntity userRoleAssignment =
        getUserRoleEntity(userEntity, userRole);

    userEntity.setRoles(Set.of(userRoleAssignment));

    UserEntity savedUser = userRepository.save(userEntity);

    Set<Role> roles = getRoles(savedUser);

    User registeredUser =
        new User(savedUser.getId().toString(), savedUser.getUsername(), savedUser.getPassword(),
            savedUser.getCreatedAt(), roles);

    try {
      String json = objectMapper.writeValueAsString(registeredUser);

      kafkaProducer.sendRegisteredUserEvent(json);
    } catch (JsonProcessingException e) {
      log.error("Exception: ", e);
      throw new RuntimeException(e);
    }

    return registeredUser;
  }

  public User getUser(GetUser user) {
    Optional<UserEntity> foundUser = userRepository.findByUsername(user.username());

    if (foundUser.isEmpty()) {
      throw new NotFoundUserException("User was not found by username: " + user.username());
    }

    UserEntity userEntity = foundUser.get();
    String encodedPassword = passwordEncoder.encode(user.password());

    if (!encodedPassword.equals(userEntity.getPassword())) {
      throw new InvalidPasswordException("Password or username is invalid");
    }

    Set<Role> roles = getRoles(userEntity);

    return new User(userEntity.getId().toString(), user.password(), "*******",
        userEntity.getCreatedAt(), roles);
  }

  @Transactional
  public CreatedRefreshToken createRefreshToken(User user) {
    UserEntity userEntity = userRepository.getReferenceById(UUID.fromString(user.uuid()));

    RefreshTokenEntity refreshTokenEntity = getRefreshTokenEntity(userEntity);
    RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshTokenEntity);

    String jwtToken = jwtUtil.generateAccessToken(UUID.fromString(user.uuid()), user.username());

    return new CreatedRefreshToken(jwtToken, savedRefreshToken.getToken());
  }

  public boolean validateToken(String token) {
    return jwtUtil.validateToken(token);
  }

  public User getUserByToken(String token) {
    UUID userId = jwtUtil.getUserIdFromToken(token);

    Optional<UserEntity> foundUser = userRepository.findById(userId);

    if (foundUser.isEmpty()) {
      throw new NotFoundUserException("User was not found by id: " + userId);
    }

    UserEntity userEntity = foundUser.get();

    Set<Role> roles = getRoles(userEntity);

    return new User(userEntity.getId().toString(), userEntity.getUsername(), "*******",
        userEntity.getCreatedAt(), roles);
  }

  public void logoutUser(String token) {
    UUID userId = jwtUtil.getUserIdFromToken(token);

    Optional<UserEntity> foundUser = userRepository.findById(userId);

    if (foundUser.isEmpty()) {
      throw new NotFoundUserException("User was not found by id: " + userId);
    }

    Optional<RefreshTokenEntity> foundRefreshToken =
        refreshTokenRepository.findByUser(foundUser.get());

    if (foundRefreshToken.isEmpty()) {
      throw new NotFoundRefreshTokenException("Refresh token was not found by id: " + userId);
    }

    refreshTokenRepository.delete(foundRefreshToken.get());
  }

  public CreatedRefreshToken refreshAccessToken(String refreshTokenString) {
    RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(refreshTokenString)
        .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

    if (refreshToken.getExpiryDate().isBefore(LocalDate.now())) {
      refreshTokenRepository.delete(refreshToken);
      throw new RefreshTokenExpiredException("Refresh token expired, please login again");
    }

    UserEntity user = refreshToken.getUser();

    String jwtToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpiryDate(LocalDate.now().plusDays(7));

    refreshTokenRepository.save(refreshToken);

    return new CreatedRefreshToken(jwtToken, refreshToken.getToken());
  }

  private static RefreshTokenEntity getRefreshTokenEntity(UserEntity userEntity) {
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();

    refreshTokenEntity.setId(UUID.randomUUID());
    refreshTokenEntity.setUser(userEntity);
    refreshTokenEntity.setToken(UUID.randomUUID().toString());
    refreshTokenEntity.setExpiryDate(LocalDate.now().plusDays(7));

    return refreshTokenEntity;
  }

  private static Set<Role> getRoles(UserEntity userEntity) {
    return userEntity.getRoles().stream().map((userRoleEntity -> {
      RoleEntity roleEntity = userRoleEntity.getRole();

      return new Role(roleEntity.getId().toString(), roleEntity.getName());
    })).collect(Collectors.toSet());
  }

  private static UserRoleEntity getUserRoleEntity(UserEntity userEntity, RoleEntity userRole) {
    UserRoleEntity userRoleAssignment = new UserRoleEntity();
    userRoleAssignment.setId(new UserRoleId(userEntity.getId(), userRole.getId()));
    userRoleAssignment.setUser(userEntity);
    userRoleAssignment.setRole(userRole);
    return userRoleAssignment;
  }
}
