package com.taskmanagement.task.service.exception;

public class NotFoundTaskException extends RuntimeException {

  public NotFoundTaskException() {
  }

  public NotFoundTaskException(String message) {
    super(message);
  }
}
