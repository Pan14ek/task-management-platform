package com.taskmanagement.auth.repository;

import com.taskmanagement.auth.entity.RefreshTokenEntity;
import com.taskmanagement.auth.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

  Optional<RefreshTokenEntity> findByUser(UserEntity userEntity);

}
