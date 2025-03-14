package com.taskmanagement.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(unique = true, nullable = false, name = "username")
  private String username;

  @Column(nullable = false, name = "password")
  @JsonIgnore
  private String password;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<UserRoleEntity> roles;

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
        Objects.equals(username, that.username) &&
        Objects.equals(password, that.password) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(roles, that.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, createdAt);
  }
}
