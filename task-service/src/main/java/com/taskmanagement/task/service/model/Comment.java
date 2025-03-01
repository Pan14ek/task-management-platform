package com.taskmanagement.task.service.model;

public record Comment(String id, String taskId, String userId, String content) {
}
