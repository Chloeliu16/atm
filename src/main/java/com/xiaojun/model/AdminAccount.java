package com.xiaojun.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an administrator account in the system.
 * This entity is mapped to a database table with fields for account identification,
 * login credentials, and security pin code.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccount {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long accountId;

  private String login;
  private String pincode;
}

