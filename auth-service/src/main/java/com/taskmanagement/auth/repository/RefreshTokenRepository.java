package com.taskmanagement.auth.repository;

import com.taskmanagement.auth.entity.RefreshTokenEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
}
