package com.taskmanagement.task.service;

import com.taskmanagement.task.entity.CommentEntity;
import com.taskmanagement.task.entity.TaskEntity;
import com.taskmanagement.task.repository.CommentRepository;
import com.taskmanagement.task.repository.TaskRepository;
import com.taskmanagement.task.service.model.Comment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final TaskRepository taskRepository;

  @Transactional
  public Comment addComment(String taskId, String userId, String content) {
    TaskEntity task = taskRepository.findById(UUID.fromString(taskId))
        .orElseThrow(() -> new EntityNotFoundException("Task not found"));

    CommentEntity commentEntity = CommentEntity.builder()
        .id(UUID.randomUUID())
        .task(task)
        .userId(UUID.fromString(userId))
        .content(content)
        .build();

    CommentEntity savedComment = commentRepository.save(commentEntity);

    return new Comment(savedComment.getId().toString(), taskId, userId, content);
  }

  public List<Comment> getCommentsByTaskId(String taskId) {
    return commentRepository.findByTaskId(UUID.fromString(taskId)).stream()
        .map(c -> new Comment(c.getId().toString(), taskId, c.getUserId().toString(),
            c.getContent()))
        .toList();
  }

  @Transactional
  public void deleteComment(String commentId) {
    commentRepository.deleteById(UUID.fromString(commentId));
  }

}
