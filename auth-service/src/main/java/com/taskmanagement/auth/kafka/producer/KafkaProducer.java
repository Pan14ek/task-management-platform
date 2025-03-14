package com.taskmanagement.auth.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendRegisteredUserEvent(String newUser) {
    kafkaTemplate.send("user-registered-topic", newUser)
        .thenAccept(result -> log.info("Sent message: {}", result))
        .exceptionally(ex -> {
          log.error("Failed to send Kafka message", ex);
          return null;
        });
  }

}
