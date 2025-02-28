package com.taskmanagement.task.dto;

import java.time.LocalDate;

public record CreateTaskRequest(
    String title,
    String description,
    String status,
    int priority,
    LocalDate createdAt,
    LocalDate dueDate,
    String assignedUserId) {
}
