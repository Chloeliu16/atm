package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the operation for withdrawing cash from a customer's account.
 * This class manages the withdrawal process, ensuring that the amount withdrawn does not exceed the account balance.
 */
@Service
public class Withdraw implements ICustomerOperate {
  @Autowired
  private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;
  /**
   * Constructs a Withdraw service with dependencies injected via Spring's @Autowired annotation.
   *
   * @param scanner Scanner object to read user inputs.
   * @param customerAccountRepository Repository to interact with customer account data.
   */

  public Withdraw(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }
  /**
   * Executes the withdrawal operation for a customer's account based on the provided login identifier.
   * Prompts the user to enter an amount to withdraw, checks if the balance is sufficient, and updates the account accordingly.
   *
   * @param login The login identifier of the customer making the withdrawal.
   */

  public void customerOperate(String login) {

    try {
      while (true) {
        System.out.println("===Withdraw Interface===");
        CustomerAccount account = customerAccountRepository.findByUsername(login);
        double balance = account.getBalance();
        System.out.println("Your current balance is: " + balance);
        System.out.println("Please enter the amount you wish to withdraw");
        double amount = scanner.nextDouble();
        if (amount <= balance) {
          account.setBalance(account.getBalance() - amount);
          customerAccountRepository.saveAndFlush(account);
          System.out.println("Cash Successfully Withdrawn!");
          System.out.println("Account #" + account.getAccountid());
          LocalDateTime now = LocalDateTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          String formattedDate = now.format(formatter);
          System.out.println("Date: " + formattedDate);
          System.out.println("Withdrawn: " + amount);
          System.out.println("Balance: " + account.getBalance());
          System.out.println("===Please press any number to return===");
          String command = scanner.next();
          return;
        } else {
          System.out.println("***Withdraw failed, please enter the correct amount***");
        }
      }
    } catch (DataAccessException e) {
      System.out.println("Error accessing data: " + e.getMessage());
    }
  }
}
