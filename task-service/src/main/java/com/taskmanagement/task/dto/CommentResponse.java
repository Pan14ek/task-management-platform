package com.taskmanagement.task.dto;

import com.taskmanagement.task.service.model.Comment;

public record CommentResponse(String id, String taskId, String userId, String content) {
  public CommentResponse(Comment comment) {
    this(comment.id(), comment.taskId(), comment.userId(), comment.content());
  }
}
