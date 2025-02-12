package com.taskmanagement.auth.service.model;

public record CreatedRefreshToken(String jwtToken, String refreshToken) {
}
