package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for searching customer accounts. Implements administrative operations
 * allowing an admin to query and display details of customer accounts.
 */
@Service
public class SearchAccount implements IAdminOperate {
  @Autowired
  private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;

  public SearchAccount(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }
  /**
     * Administers the operation for searching customer accounts based on account number.
     * Continuously prompts the admin to enter account numbers to search and displays account details.
     * Allows the admin to exit or continue searching.
     */

  public void adminOperate() {
    while (true) {
      System.out.println("Please enter the Account# you want to search: ");
      Long accountId = scanner.nextLong();
      CustomerAccount exist = customerAccountRepository.findByAccountid(accountId);
      if (exist != null) {
        System.out.println("Account detail is: ");
        System.out.println("Account# " + exist.getAccountid());
        System.out.println("login: " + exist.getUsername());
        System.out.println("pinCode: " + exist.getPincode());
        System.out.println("holderName: " + exist.getName());
        System.out.println("balance: " + exist.getBalance());
        System.out.println("Status: " + exist.getStatus());
        System.out.println("===Press 0 to exit, press 9 to continue searching===");
        int command = scanner.nextInt();
        if (command == 0) {
          return;
        }
      } else {
        System.out.println("***The account doesn't exit***");
        System.out.println("***Press 9 to continue searching***");
        System.out.println("===If you no longer wish to continue searching, press 0 to exit===");
        int command = scanner.nextInt();
        if (command == 0) {
          return;
        }
      }
    }
  }
}
