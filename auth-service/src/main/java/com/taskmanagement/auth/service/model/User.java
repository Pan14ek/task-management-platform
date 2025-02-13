package com.taskmanagement.auth.service.model;

import java.time.LocalDate;
import java.util.Set;

public record User(String uuid, String username, String password, LocalDate createdAt, Set<Role> roles) {
}
