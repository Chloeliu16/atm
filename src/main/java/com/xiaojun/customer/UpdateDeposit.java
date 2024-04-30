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
 * Service class that implements the operation for depositing cash into a customer's account.
 * This class manages the input of deposit amounts and updates the account balance accordingly.
 */
@Service
public class UpdateDeposit implements ICustomerOperate {
  @Autowired
  private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;
  /**
   * Constructs an UpdateDeposit service with dependencies injected via Spring's @Autowired annotation.
   *
   * @param scanner Scanner object to read user inputs.
   * @param customerAccountRepository Repository to interact with customer account data.
   */

  public UpdateDeposit(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }
  /**
   * Executes the deposit operation for a customer's account based on the provided login identifier.
   * Prompts the user to enter an amount to deposit, updates the account balance, and confirms the transaction with a detailed receipt.
   *
   * @param login The login identifier of the customer making the deposit.
   */

  public void customerOperate(String login) {

    System.out.println("Enter the cash amount to deposit: ");
    double amount = scanner.nextDouble();
    CustomerAccount account = customerAccountRepository.findByUsername(login);
    account.setBalance(account.getBalance() + amount);
    customerAccountRepository.saveAndFlush(account);
    System.out.println("Deposit Successfully! Your current balance is: " + account.getBalance());
    System.out.println("Cash deposit successfully.");
    System.out.println("Account #" + account.getAccountid());
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDate = now.format(formatter);
    System.out.println("Date: " + formattedDate);
    System.out.println("Deposit: " + amount);
    System.out.println("Balance: " + account.getBalance());
    System.out.println("===Please press any number to return===");
    String command = scanner.next();

  }
}
