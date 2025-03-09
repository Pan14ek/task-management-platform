package com.taskmanagement.user.dto;

import java.time.LocalDate;

public record UserProfileResponse(
    String id,
    String fullName,
    String email,
    String phone,
    String avatarUrl,
    LocalDate createdAt,
    LocalDate updatedAt
) {
}
