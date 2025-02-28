package com.taskmanagement.task.service.model;

import java.time.LocalDate;

public record NewTask(
    String title,
    String description,
    String status,
    int priority,
    LocalDate createdAt,
    LocalDate dueDate,
    String assignedUserId) {
}
