package com.taskmanagement.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  private UUID id;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(fullName, that.fullName) &&
        Objects.equals(email, that.email) && Objects.equals(phone, that.phone) &&
        Objects.equals(avatarUrl, that.avatarUrl) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fullName, email, phone, avatarUrl, createdAt, updatedAt);
  }

}
