package com.taskmanagement.task.service.model;

import java.time.LocalDate;

public record Task(
    String id,
    String title,
    String description,
    String status,
    int priority,
    LocalDate createdAt,
    LocalDate dueDate,
    String assignedUserId
) {
}
