package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Marks this class as a Spring component, making it eligible for
 * Spring's component scanning to detect and register it as a bean in the Spring application context.
 */
@Component
public class DisplayAccountDetail {
  @Autowired
  private final CustomerAccountRepository customerAccountRepository;
  /**Constructor for DisplayAccountDetail class that
   * initializes the customerAccountRepository field.
   */

  private DisplayAccountDetail(
      CustomerAccountRepository customerAccountRepository) {
    this.customerAccountRepository = customerAccountRepository;
  }
  /** Method to display account details for a given account ID.
   */

  public void display(Long id) {
    CustomerAccount exist = customerAccountRepository.findByAccountid(id);
    System.out.println("Account# " + exist.getAccountid());
    System.out.println("login: " + exist.getUsername());
    System.out.println("pinCode: " + exist.getPincode());
    System.out.println("holderName: " + exist.getName());
    System.out.println("balance: " + exist.getBalance());
    System.out.println("Status: " + exist.getStatus());
  }
}
