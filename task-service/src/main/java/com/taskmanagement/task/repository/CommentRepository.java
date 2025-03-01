package com.taskmanagement.task.repository;

import com.taskmanagement.task.entity.CommentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

  List<CommentEntity> findByTaskId(UUID taskId);

}
