package com.xiaojun.repository;

import com.xiaojun.modle.AdminAccount;
import com.xiaojun.modle.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    CustomerAccount findByUsername(String loginName);
    CustomerAccount findByUsernameAndPincode(String login, String pinCode);
    CustomerAccount findByAccountid(Long accountId);
    CustomerAccount saveAndFlush(CustomerAccount customerAccount);
}

