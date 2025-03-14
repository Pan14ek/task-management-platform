package com.taskmanagement.user.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.user.entity.UserEntity;
import com.taskmanagement.user.repository.UserRepository;
import com.taskmanagement.user.service.model.User;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserKafkaConsumer {

  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "user-registered-topic", groupId = "user-service-group")
  public void handleUserRegistered(String newUser) {
    try {
      User seriliazedUser = objectMapper.readValue(newUser, User.class);

      log.info("Received Kafka event for user registration: {}", seriliazedUser.username());

      if (userRepository.existsById(UUID.fromString(seriliazedUser.uuid()))) {
        log.warn("User with id {} already exists. Skipping.", seriliazedUser.uuid());
        return;
      }

      UserEntity userEntity = new UserEntity();

      userEntity.setId(UUID.fromString(seriliazedUser.uuid()));
      userEntity.setCreatedAt(seriliazedUser.createdAt());
      userEntity.setUpdatedAt(LocalDate.now());
      userEntity.setEmail("");
      userEntity.setFullName("");
      userEntity.setAvatarUrl("");
      userEntity.setPhone("");

      UserEntity savedUser = userRepository.save(userEntity);

      log.info("User successfully saved: {}", savedUser.getId().toString());

    } catch (Exception e) {
      log.error("Failed converting user from json ", e);
    }
  }

}
