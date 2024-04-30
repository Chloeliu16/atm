package com.xiaojun.repository;

import com.xiaojun.model.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for {@link AdminAccount} data operations.
 * Extends JpaRepository to provide basic CRUD operations and query method execution for admin accounts.
 * Utilizes Spring Data JPA to reduce boilerplate data access code and improve implementation efficiency.
 */
@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
  AdminAccount findByLoginAndPincode(String loginname, String pincode);
}


