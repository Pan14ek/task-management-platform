package com.taskmanagement.task.api;

import com.taskmanagement.task.dto.CommentRequest;
import com.taskmanagement.task.dto.CommentResponse;
import com.taskmanagement.task.service.CommentService;
import com.taskmanagement.task.service.model.Comment;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {

  private final CommentService commentService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public ResponseEntity<CommentResponse> addComment(@PathVariable String taskId,
                                                    @RequestBody CommentRequest request) {
    Comment comment = commentService.addComment(taskId, request.userId(), request.content());

    return ResponseEntity.ok(new CommentResponse(comment));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResponseEntity<List<CommentResponse>> getComments(@PathVariable String taskId) {
    List<Comment> comments = commentService.getCommentsByTaskId(taskId);
    return ResponseEntity.ok(comments.stream().map(CommentResponse::new).toList());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable String commentId) {
    commentService.deleteComment(commentId);
    return ResponseEntity.ok("Comment deleted successfully");
  }

}
