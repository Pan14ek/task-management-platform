package com.taskmanagement.auth.service.exception;

public class NotFoundRefreshTokenException extends RuntimeException {
  public NotFoundRefreshTokenException(String message) {
    super(message);
  }
}
