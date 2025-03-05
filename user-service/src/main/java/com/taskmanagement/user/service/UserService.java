package com.taskmanagement.user.service;

import com.taskmanagement.user.entity.UserEntity;
import com.taskmanagement.user.repository.UserRepository;
import com.taskmanagement.user.service.exception.NotFoundUserException;
import com.taskmanagement.user.service.model.ModifiedUserProfile;
import com.taskmanagement.user.service.model.UserProfile;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserProfile getUserProfile(String id) {
    Optional<UserEntity> foundUserProfile = userRepository.findById(UUID.fromString(id));

    if (foundUserProfile.isEmpty()) {
      throw new NotFoundUserException("User with id " + id + " not found");
    }

    UserEntity user = foundUserProfile.get();

    return obtainUserProfile(user);
  }

  public UserProfile updateUserProfile(ModifiedUserProfile modifiedUserProfile) {
    Optional<UserEntity> foundUserProfile =
        userRepository.findById(UUID.fromString(modifiedUserProfile.id()));

    if (foundUserProfile.isEmpty()) {
      throw new NotFoundUserException("User with id " + modifiedUserProfile.id() + " not found");
    }

    UserEntity userEntity = foundUserProfile.get();

    userEntity.setId(UUID.fromString(modifiedUserProfile.id()));
    userEntity.setEmail(modifiedUserProfile.email());
    userEntity.setFullName(modifiedUserProfile.fullName());
    userEntity.setAvatarUrl(modifiedUserProfile.avatarUrl());

    UserEntity updatedUserProfile = userRepository.save(userEntity);

    return obtainUserProfile(updatedUserProfile);
  }

  private static UserProfile obtainUserProfile(UserEntity user) {
    return new UserProfile(
        user.getId().toString(),
        user.getFullName(),
        user.getEmail(),
        user.getPhone(),
        user.getAvatarUrl(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
