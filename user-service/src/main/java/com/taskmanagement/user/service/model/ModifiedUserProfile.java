package com.taskmanagement.user.service.model;

public record ModifiedUserProfile(String id,
                                  String fullName,
                                  String email,
                                  String phone,
                                  String avatarUrl) {
}
