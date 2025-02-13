package com.taskmanagement.auth.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_roles")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

  @EmbeddedId
  private UserRoleId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private RoleEntity role;

}
