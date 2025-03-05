package com.taskmanagement.user.service.model;

import java.time.LocalDate;

public record UserProfile(
    String id,
    String fullName,
    String email,
    String phone,
    String avatarUrl,
    LocalDate createdAt,
    LocalDate updatedAt
) {
}
