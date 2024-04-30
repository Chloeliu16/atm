package com.xiaojun.admin;

import com.xiaojun.login.ILogin;
import com.xiaojun.model.AdminAccount;
import com.xiaojun.repository.AdminAccountRepository;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** Component responsible for handling the login process for administrators.
   * It facilitates authentication against stored admin credentials and provides access to the admin user interface upon successful login.
 */
@Component
public class AdminLogin implements ILogin {
  private final AdminUI adminUI;
  private final Scanner scanner;
  private final AdminAccountRepository adminAccountRepository;
  /**
    * Constructs an AdminLogin instance with necessary dependencies.
    *
    * @param scanner Scanner object to handle user input.
    * @param adminUI User interface component for admin functions.
    * @param adminAccountRepository Repository to access admin account credentials.
    */

  @Autowired
  public AdminLogin(Scanner scanner, AdminUI adminUI, AdminAccountRepository adminAccountRepository) {
    this.scanner = scanner;
    this.adminUI = adminUI;
    this.adminAccountRepository = adminAccountRepository;
  }
  /**
    * Handles the account login process for administrators. Prompts for username and password,
    * validates them against the repository, and grants access to the admin UI upon successful authentication.
    * Users can exit the login process by typing 'exit'.
    */

  public void accountLogin() {
    try {
      System.out.println("Connect successfully！");

      while (true) {
        System.out.println("Enter username (type 'exit' to quit): ");
        String username = scanner.next();
        if ("exit".equalsIgnoreCase(username)) {
          System.out.println("Exiting login process...");
          break;
        }
        System.out.println("Enter password: ");
        String password = scanner.next();
        try {
          AdminAccount exist = adminAccountRepository.findByLoginAndPincode(username, password);
          if (exist != null) {
            adminUI.showAdminUI();
            break;
          } else {
            System.out.println("***Wrong info, try again!***");
          }
        } catch (RuntimeException e) {
          System.out.println("Error accessing data: " + e.getMessage());
          break; // or continue based on your application logic
        }
      }
    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
    }
  }
}
