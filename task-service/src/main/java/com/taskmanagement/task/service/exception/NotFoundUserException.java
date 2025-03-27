package com.taskmanagement.task.service.exception;

public class NotFoundUserException extends RuntimeException {

  public NotFoundUserException() {
  }

  public NotFoundUserException(String message) {
    super(message);
  }
}
