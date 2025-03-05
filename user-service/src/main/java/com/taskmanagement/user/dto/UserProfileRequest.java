package com.taskmanagement.user.dto;

public record UserProfileRequest(
    String id,
    String fullName,
    String email,
    String phone,
    String avatarUrl
) {
}
