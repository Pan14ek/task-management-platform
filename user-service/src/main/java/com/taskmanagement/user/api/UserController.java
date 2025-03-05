package com.taskmanagement.user.api;

import com.taskmanagement.user.dto.UserProfileRequest;
import com.taskmanagement.user.dto.UserProfileResponse;
import com.taskmanagement.user.service.UserService;
import com.taskmanagement.user.service.model.ModifiedUserProfile;
import com.taskmanagement.user.service.model.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}")
  public ResponseEntity<UserProfile> getUserProfile(@PathVariable String id) {
    return ResponseEntity.ok(userService.getUserProfile(id));
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping
  public ResponseEntity<UserProfileResponse> updateUserProfile(
      @RequestBody UserProfileRequest userProfile) {

    ModifiedUserProfile modifiedUserProfile = new ModifiedUserProfile(
        userProfile.id(),
        userProfile.fullName(),
        userProfile.email(),
        userProfile.phone(),
        userProfile.avatarUrl()
    );

    UserProfile updatedUserProfile = userService.updateUserProfile(modifiedUserProfile);

    UserProfileResponse userProfileResponse = new UserProfileResponse(
        updatedUserProfile.id(),
        updatedUserProfile.fullName(),
        updatedUserProfile.email(),
        updatedUserProfile.phone(),
        updatedUserProfile.avatarUrl(),
        updatedUserProfile.createdAt(),
        updatedUserProfile.updatedAt()
    );

    return ResponseEntity.ok(userProfileResponse);
  }

}
