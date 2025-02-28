package com.taskmanagement.task.entity;


import jakarta.persistence.Column;
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

  @Column(nullable = false, name = "user_id")
  private UUID userId;

  @Column(nullable = false, name = "content")
  private String content;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private TaskEntity task;

}
