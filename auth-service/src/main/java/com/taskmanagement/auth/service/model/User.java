package com.taskmanagement.auth.service.model;

import java.time.LocalDate;

public record User(String uuid, String username, String password, LocalDate createdAt) {
}
