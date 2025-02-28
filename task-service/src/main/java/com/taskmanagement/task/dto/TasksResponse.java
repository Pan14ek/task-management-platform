package com.taskmanagement.task.dto;

import com.taskmanagement.task.service.model.Task;
import java.util.List;

public record TasksResponse(List<Task> tasks) {
}
