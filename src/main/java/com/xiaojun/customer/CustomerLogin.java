package com.xiaojun.customer;

import com.xiaojun.login.ILogin;
import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Component responsible for managing customer login processes.
 * This class handles the authentication of customer credentials and provides access to the customer user interface upon successful login.
 */
@Component
public class CustomerLogin implements ILogin {
  private final CustomerAccountRepository customerAccountRepository;
  private final CustomerUI customerUI;
  private final Scanner scanner;
  /**
   * Constructs a CustomerLogin with necessary dependencies for handling login processes.
   *
   * @param scanner Scanner object to read user inputs.
   * @param customerAccountRepository Repository to interact with customer account data.
   * @param customerUI User interface component for customer interactions after login.
   */

  public CustomerLogin(
      Scanner scanner,
      CustomerAccountRepository customerAccountRepository,
      CustomerUI customerUI) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
    this.customerUI = customerUI;
  }
  /**
   * Handles the account login process for customers. Prompts for login and pin code,
   * validates them against the stored credentials, and grants access to the customer UI upon successful authentication.
   * If authentication fails, the user is prompted to try again.
   */

  @SuppressWarnings("checkstyle:Indentation")
  @Override
  public void accountLogin() {
    System.out.println("Connect system successfully!");
    while (true) {
      System.out.println("Enter login name (type 'exit' to quit): ");
      String login = scanner.next();
      if ("exit".equalsIgnoreCase(login)) {
        System.out.println("Exiting login process.");
        break;
      }
      System.out.println("Enter Pin code: ");
      String pinCode = scanner.next();
      CustomerAccount customer = customerAccountRepository.findByUsernameAndPincode(login, pinCode);
      if (customer != null) {
        customerUI.showCustomerUI(login);
        break;
      } else {
        System.out.println("***Your info is wrong, please try again!***");
      }
    }
  }
}
