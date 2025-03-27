package com.taskmanagement.task.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdCheckResponse {

  private String correlationId;
  private boolean userExists;

}
