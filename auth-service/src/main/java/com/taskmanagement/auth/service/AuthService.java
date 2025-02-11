package com.taskmanagement.auth.service;

import com.taskmanagement.auth.entity.UserEntity;
import com.taskmanagement.auth.repository.UserRepository;
import com.taskmanagement.auth.service.model.NewUser;
import com.taskmanagement.auth.service.model.User;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  @Transactional
  public User registerUser(NewUser newUser) {
    UserEntity userEntity = new UserEntity();

    userEntity.setId(UUID.randomUUID());
    userEntity.setUsername(newUser.username());
    userEntity.setPassword(newUser.password());
    userEntity.setCreatedAt(LocalDate.now());

    UserEntity savedUser = userRepository.save(userEntity);

    return new User(savedUser.getId().toString(), savedUser.getUsername(), savedUser.getPassword(),
        savedUser.getCreatedAt());
  }

}
