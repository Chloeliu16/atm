package com.xiaojun.repository;

import com.xiaojun.model.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
  AdminAccount findByLoginAndPincode(String loginname, String pincode);
}


