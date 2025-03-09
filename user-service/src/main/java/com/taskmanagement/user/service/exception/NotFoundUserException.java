package com.taskmanagement.user.service.exception;

public class NotFoundUserException extends RuntimeException {

  public NotFoundUserException(String message) {
    super(message);
  }
}
