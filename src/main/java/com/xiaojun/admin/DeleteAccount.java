package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * Service class that implements the administrative operation for deleting customer accounts.
 * This class handles the user input required to gather details for securely deleting an existing account from the system.
 */
@Service
public class DeleteAccount implements IAdminOperate {

  @Autowired
    private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;
  /**
   * Constructor for DeleteAccount service, initializes the scanner used for input.
   *
   * @param scanner The scanner object to handle user input.
   */

  public DeleteAccount(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }
  /**
   * Implements the admin operation to delete a customer account. Prompts the administrator to confirm the deletion
   * before permanently removing the account from the database.
   */

  public void adminOperate() {
    try {
      while (true) {
        System.out.println("===Delete Interface===");
        System.out.println("===Please input the login name you want to delete===");

        String loginName = scanner.next();

        CustomerAccount exist = customerAccountRepository.findByUsername(loginName);
        if (exist != null) {
          System.out.println("The login name you want to delete is: " + exist.getUsername());
          System.out.println("If you are certain that you want to delete, please enter 9.");
          String number = scanner.next();
          if ("9".equals(number)) {
            customerAccountRepository.delete(exist);
            System.out.println("Account deleted successfully.");
            System.out.println("Press any number to exit");
            String command = scanner.next();
            return;
          }
        } else {
          System.out.println("No account found with the given login name.");
          System.out.println("If you want to cancel delete operation, press 0 to back");
          String delete = scanner.next();
          if ("0".equals(delete)) {
            return;
          }
        }
      }
    } catch (DataAccessException e) {
      System.out.println("Error accessing data: " + e.getMessage());
    }
  }
}

