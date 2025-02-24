package com.taskmanagement.task.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Table
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class TaskEntity {

  @Id
  private UUID id;

  @Column(nullable = false, name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private String status;

  @Column(name = "priority")
  private int priority;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "assigned_user")
  private UUID assignedUser;

  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CommentEntity> comments;
}
