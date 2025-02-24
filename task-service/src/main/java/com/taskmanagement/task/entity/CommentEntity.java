package com.taskmanagement.task.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Table
@Entity
public class CommentEntity {

  @Id
  private UUID id;

  private UUID userId;

  private String content;

  private LocalDateTime createdAt;

  @ManyToOne
  @MapsId("taskId")
  @JoinColumn(name = "comment_id")
  private TaskEntity task;

}
