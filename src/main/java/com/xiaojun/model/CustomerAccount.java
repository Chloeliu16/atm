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
 * Represents a customer account in a financial or service-oriented system.
 * This entity class is used to map customer account details to a database table
 * including information such as username, pincode, name, balance, and status.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccount {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long accountid;

  private String username;
  private String pincode;
  private String name;
  private double balance;
  private String status;
}

