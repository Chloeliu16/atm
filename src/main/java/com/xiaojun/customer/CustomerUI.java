package com.xiaojun.customer;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component that serves as the user interface for customer actions within the application.
 * This class manages customer interactions by providing options for various account operations like withdrawing, depositing, and displaying balance.
 */
@Component
public class CustomerUI {
  @Autowired
  private final ICustomerOperate displayBalance;
  private final ICustomerOperate updateDeposit;
  private final ICustomerOperate withdraw;
  private final Scanner scanner;
  /**
   * Constructs a CustomerUI with necessary components for handling customer operations.
   *
   * @param scanner Scanner object to read user commands.
   * @param displayBalance Component that displays the balance of the customer.
   * @param updateDeposit Component that handles deposit operations.
   * @param withdraw Component that handles withdrawal operations.
   */

  public CustomerUI(
        Scanner scanner,
        ICustomerOperate displayBalance,
        ICustomerOperate updateDeposit,
        ICustomerOperate withdraw) {
    this.scanner = scanner;
    this.displayBalance = displayBalance;
    this.updateDeposit = updateDeposit;
    this.withdraw = withdraw;
  }
  /**
   * Displays the customer user interface and handles the selection of various banking operations.
   * Continuously prompts the customer to choose an action and delegates the task to the appropriate component based on the selection.
   *
   * @param login The login identifier of the customer using the interface.
   */

  public void showCustomerUI(String login)  {
    while (true) {
      System.out.println("===Welcome to Customer Interface===");
      System.out.println("===You can select one of the following options: ===");
      System.out.println("1----Withdraw Cash");
      System.out.println("3----Deposit Cash");
      System.out.println("4----Display Balance");
      System.out.println("5----Exit");

      try {
        int command = scanner.nextInt();
        switch (command) {
          case 1:
            //withdraw
            withdraw.customerOperate(login);
            break;
          case 3:
            //deposit
            updateDeposit.customerOperate(login);
            break;
          case 4:
            //search for account
            displayBalance.customerOperate(login);
            break;
          case 5:
            //exit
            System.out.println("You have logged out.");
            return;
          default:
            System.out.println("***Your operation is incorrect, please select again.***");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }
}
