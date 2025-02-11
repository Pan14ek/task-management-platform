package com.taskmanagement.auth.dto;

public record LoginResponse(String accessToken, String refreshToken) {
}
