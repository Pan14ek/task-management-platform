package com.taskmanagement.task.api;

import com.taskmanagement.task.dto.AssignTaskRequest;
import com.taskmanagement.task.dto.CreateTaskRequest;
import com.taskmanagement.task.dto.TaskResponse;
import com.taskmanagement.task.dto.TasksResponse;
import com.taskmanagement.task.service.TaskService;
import com.taskmanagement.task.service.model.NewTask;
import com.taskmanagement.task.service.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TaskController {

  private static final String BEARER_PREFIX = "Bearer ";
  private static final String AUTHORIZATION = "Authorization";

  private TaskService taskService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/tasks")
  public ResponseEntity<TasksResponse> getTasks(
      @RequestHeader(value = AUTHORIZATION, required = false) String token) {
    if (token == null || !token.startsWith(BEARER_PREFIX)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new TasksResponse(null));
    }

    return ResponseEntity.ok(new TasksResponse(taskService.getTasks()));
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/tasks")
  public ResponseEntity<TaskResponse> createTask(
      @RequestBody CreateTaskRequest createTaskRequest) {
    NewTask newTask = new NewTask(
        createTaskRequest.title(),
        createTaskRequest.description(),
        createTaskRequest.status(),
        createTaskRequest.priority(),
        createTaskRequest.createdAt(),
        createTaskRequest.dueDate(),
        createTaskRequest.assignedUserId());

    Task createdNewTask = taskService.createNewTask(newTask);

    TaskResponse taskResponse = getCreateTaskResponse(createdNewTask);

    return ResponseEntity.ok(taskResponse);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/tasks/{id}")
  public ResponseEntity<TaskResponse> getTaskById(@PathVariable String id) {
    Task foundTask = taskService.getTaskById(id);

    return ResponseEntity.ok(getCreateTaskResponse(foundTask));
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/tasks/{id}")
  public ResponseEntity<TaskResponse> updateTask(@PathVariable String id,
                                                 @RequestBody CreateTaskRequest createTaskRequest) {
    Task task = new Task(
        id,
        createTaskRequest.title(),
        createTaskRequest.description(),
        createTaskRequest.status(),
        createTaskRequest.priority(),
        createTaskRequest.createdAt(),
        createTaskRequest.dueDate(),
        createTaskRequest.assignedUserId());

    Task updatedTask = taskService.updateTask(task);

    return ResponseEntity.ok(getCreateTaskResponse(updatedTask));
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<String> deleteTaskById(@PathVariable String id) {

    boolean isDeletedTask = taskService.deleteTaskById(id);

    return ResponseEntity.ok(isDeletedTask ? "Deleted the task by id + " + id : "Not Deleted");
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/tasks/{id}/assign")
  public ResponseEntity<TaskResponse> assignTaskToUser(@PathVariable String id,
                                                       @RequestBody AssignTaskRequest request) {
    Task task = taskService.assignTask(id, request.userId());

    return ResponseEntity.ok(new TaskResponse(
        task.id(),
        task.title(),
        task.description(),
        task.status(),
        task.priority(),
        task.createdAt(),
        task.dueDate(),
        task.assignedUserId()
    ));
  }

  private static TaskResponse getCreateTaskResponse(Task createdNewTask) {
    return new TaskResponse(
        createdNewTask.id(),
        createdNewTask.title(),
        createdNewTask.description(),
        createdNewTask.status(),
        createdNewTask.priority(),
        createdNewTask.createdAt(),
        createdNewTask.dueDate(),
        createdNewTask.assignedUserId()
    );
  }

}
