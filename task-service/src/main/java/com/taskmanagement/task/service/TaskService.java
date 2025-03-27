package com.taskmanagement.task.service;

import com.taskmanagement.task.entity.TaskEntity;
import com.taskmanagement.task.kafka.producer.KafkaProducer;
import com.taskmanagement.task.repository.TaskRepository;
import com.taskmanagement.task.service.exception.NotFoundTaskException;
import com.taskmanagement.task.service.model.NewTask;
import com.taskmanagement.task.service.model.Task;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final KafkaProducer kafkaProducer;

  public List<Task> getTasks() {
    List<TaskEntity> taskEntities = taskRepository.findAll();

    return taskEntities.stream().map(TaskService::convertEntityToModel).toList();
  }

  @Transactional
  public Task createNewTask(NewTask newTask) {

    boolean userExists = kafkaProducer.checkUserId(newTask.assignedUserId());

    if (!userExists) {
      throw new EntityNotFoundException(
          "Assigned user not found by id " + newTask.assignedUserId());
    }

    TaskEntity taskEntity = new TaskEntity();

    taskEntity.setTitle(newTask.title());
    taskEntity.setDescription(newTask.description());
    taskEntity.setStatus(newTask.status());
    taskEntity.setPriority(newTask.priority());
    taskEntity.setCreatedAt(newTask.createdAt());
    taskEntity.setDueDate(newTask.dueDate());
    taskEntity.setAssignedUser(UUID.fromString(newTask.assignedUserId()));

    TaskEntity createdTaskEntity = taskRepository.save(taskEntity);

    return convertEntityToModel(createdTaskEntity);
  }

  public Task getTaskById(String id) {
    TaskEntity taskEntity = taskRepository.findById(UUID.fromString(id)).orElse(null);

    if (taskEntity == null) {
      throw new NotFoundTaskException("Task with id " + id + " not found");
    }

    return convertEntityToModel(taskEntity);
  }

  @Transactional
  public Task updateTask(Task modifiedTask) {
    Optional<TaskEntity> taskEntityOptional =
        taskRepository.findById(UUID.fromString(modifiedTask.id()));

    if (taskEntityOptional.isEmpty()) {
      throw new NotFoundTaskException("Task with id " + modifiedTask.id() + " not found");
    }

    TaskEntity taskEntity = taskEntityOptional.get();

    TaskEntity updatedTaskEntity = taskRepository.save(taskEntity);

    return convertEntityToModel(updatedTaskEntity);
  }

  @Transactional
  public boolean deleteTaskById(String id) {
    Optional<TaskEntity> taskEntityOptional =
        taskRepository.findById(UUID.fromString(id));

    if (taskEntityOptional.isEmpty()) {
      throw new NotFoundTaskException("Task with id " + id + " not found");
    }

    taskRepository.deleteById(UUID.fromString(id));

    return true;
  }

  @Transactional
  public Task assignTask(String taskId, String userId) {
    TaskEntity task = taskRepository.findById(UUID.fromString(taskId))
        .orElseThrow(() -> new EntityNotFoundException("Task not found"));

    task.setAssignedUser(UUID.fromString(userId));
    task.setUpdatedAt(LocalDate.now());

    TaskEntity updatedTask = taskRepository.save(task);

    return new Task(
        updatedTask.getId().toString(),
        updatedTask.getTitle(),
        updatedTask.getDescription(),
        updatedTask.getStatus(),
        updatedTask.getPriority(),
        updatedTask.getCreatedAt(),
        updatedTask.getUpdatedAt(),
        updatedTask.getAssignedUser().toString()
    );
  }

  private static Task convertEntityToModel(TaskEntity taskEntity) {
    return new Task(
        taskEntity.getId().toString(),
        taskEntity.getTitle(),
        taskEntity.getDescription(),
        taskEntity.getStatus(),
        taskEntity.getPriority(),
        taskEntity.getCreatedAt(),
        taskEntity.getDueDate(),
        taskEntity.getAssignedUser().toString());
  }

}
