package com.taskmanagement.user.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.user.entity.UserEntity;
import com.taskmanagement.user.repository.UserRepository;
import com.taskmanagement.user.service.model.User;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserKafkaConsumer {

  private final KafkaTemplate<String, Object> kafkaTemplate;
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

  @KafkaListener(topics = "user-id-check", groupId = "user-service-group")
  public void checkUserId(UserIdCheckRequest request) {
    log.info("Received userId check request: {}", request.getUserId());

    boolean exists = false;
    try {
      UUID uuid = UUID.fromString(request.getUserId());
      exists = userRepository.findById(uuid).isPresent();
    } catch (Exception e) {
      log.warn("Invalid UUID or DB error", e);
    }

    UserIdCheckResponse response = new UserIdCheckResponse(
        request.getCorrelationId(),
        exists
    );

    kafkaTemplate.send(request.getReplyTopic(), response);
  }

}
