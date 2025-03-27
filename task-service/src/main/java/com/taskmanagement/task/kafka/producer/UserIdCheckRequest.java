package com.taskmanagement.task.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdCheckRequest {

  private String userId;
  private String correlationId;
  private String replyTopic;

}
