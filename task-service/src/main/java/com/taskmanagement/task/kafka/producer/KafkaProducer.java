package com.taskmanagement.task.kafka.producer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final Map<String, CompletableFuture<Boolean>> responseMap = new ConcurrentHashMap<>();

  public boolean checkUserId(String userId) {
    String correlationId = UUID.randomUUID().toString();
    CompletableFuture<Boolean> future = new CompletableFuture<>();

    responseMap.put(correlationId, future);

    UserIdCheckRequest request = new UserIdCheckRequest(
        userId,
        correlationId,
        "user-id-check-response"
    );

    kafkaTemplate.send("user-id-check", request);

    try {
      // Очікуємо максимум 5 секунд
      return future.get(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      log.error("Error while waiting for user check response", e);
      return false;
    } finally {
      responseMap.remove(correlationId); // clean up
    }
  }

  @KafkaListener(topics = "user-id-check-response", groupId = "task-service-group")
  public void handleUserIdCheckResponse(UserIdCheckResponse response) {
    CompletableFuture<Boolean> future = responseMap.get(response.getCorrelationId());

    if (future != null) {
      future.complete(response.isUserExists());
    } else {
      log.warn("No waiting future for correlationId {}", response.getCorrelationId());
    }
  }
}
