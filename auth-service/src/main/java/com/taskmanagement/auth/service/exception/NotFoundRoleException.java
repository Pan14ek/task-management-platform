package com.taskmanagement.auth.service.exception;

public class NotFoundRoleException extends RuntimeException {

  public NotFoundRoleException(String message) {
    super(message);
  }
}
