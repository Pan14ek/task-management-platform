package com.taskmanagement.auth.dto;

public record LoginResponse(String token, String refreshToken) {
}
