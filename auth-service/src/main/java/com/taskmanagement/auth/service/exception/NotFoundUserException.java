package com.taskmanagement.auth.service.exception;

public class NotFoundUserException extends RuntimeException {

  public NotFoundUserException(String message) {
    super(message);
  }
}
