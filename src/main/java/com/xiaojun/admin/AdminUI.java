package com.xiaojun.admin;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component that serves as the user interface for administrative actions within the application.
 * This class handles user interactions and delegates administrative tasks such as creating, deleting,
 * updating, and searching for accounts to specific components that implement the IAdminOperate interface.
 */
@Component
public class AdminUI {
  private final IAdminOperate createAccount;
  private final IAdminOperate deleteAccount;
  private final IAdminOperate updateAccount;
  private final IAdminOperate searchAccount;
  private final Scanner scanner;

  /**
    * Constructs an AdminUI with the necessary components and a scanner for input.
    *
    * @param scanner The scanner to read user commands.
    * @param createAccount Component responsible for creating accounts.
    * @param deleteAccount Component responsible for deleting accounts.
    * @param updateAccount Component responsible for updating account information.
    * @param searchAccount Component responsible for searching for accounts.
    */
  @Autowired
    public AdminUI(Scanner scanner, IAdminOperate createAccount, IAdminOperate deleteAccount,
                   IAdminOperate updateAccount, IAdminOperate searchAccount) {
    this.scanner = scanner;
    this.createAccount = createAccount;
    this.deleteAccount = deleteAccount;
    this.updateAccount = updateAccount;
    this.searchAccount = searchAccount;
  }
  /**
    * Displays the administrative user interface and handles the selection of various administrative operations.
    * Continuously prompts the user to choose an action and delegates the task to the appropriate component.
    */

  public void showAdminUI() {
    while (true) {
      System.out.println("==Administrator Interface==");
      System.out.println("===You can select one of the following options: ===");
      System.out.println("1----Create New Account");
      System.out.println("2----Delete Existing Account");
      System.out.println("3----Update Account Information");
      System.out.println("4----Search Account");
      System.out.println("5----Exit");

      int command = scanner.nextInt();
      switch (command) {
        case 1:
          createAccount.adminOperate();
          break;
        case 2:
          deleteAccount.adminOperate();
          break;
        case 3:
          updateAccount.adminOperate();
          break;
        case 4:
          searchAccount.adminOperate();
          break;
        case 5:
          System.out.println("===You have logged out.===");
          return;
        default:
          System.out.println("***Your operation is incorrect, please select again.***");
      }
    }
  }
}
