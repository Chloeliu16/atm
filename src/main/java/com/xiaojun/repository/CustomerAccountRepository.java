package com.xiaojun.repository;

import com.xiaojun.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link CustomerAccount} entities.
 * Extends JpaRepository to provide standard CRUD operations and query executions for CustomerAccount entities.
 * This interface leverages Spring Data JPA to automatically generate implementations that handle
 * the database operations required for managing CustomerAccount records, thereby reducing the need for explicit SQL.
 */
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
  CustomerAccount findByUsername(String loginName);

  CustomerAccount findByUsernameAndPincode(String login, String pinCode);

  CustomerAccount findByAccountid(Long accountId);

  CustomerAccount saveAndFlush(CustomerAccount customerAccount);
}

