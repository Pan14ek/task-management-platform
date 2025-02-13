package com.taskmanagement.auth.service.exception;

public class RefreshTokenExpiredException extends RuntimeException {

  public RefreshTokenExpiredException(String message) {
    super(message);
  }
}
