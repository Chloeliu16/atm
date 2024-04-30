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
 * Service class that implements the customer operation for displaying account balances.
 * This class retrieves the balance information from the database and displays it to the customer.
 */
@Service
public class DisplayBalance implements ICustomerOperate {
  @Autowired
  private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;
  /**
   * Constructs a DisplayBalance service with dependencies injected via Spring's @Autowired annotation.
   *
   * @param scanner Scanner object to read user inputs.
   * @param customerAccountRepository Repository to interact with customer account data.
   */

  public DisplayBalance(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }
  /**
   * Executes the operation to display the balance of a customer's account.
   * Retrieves the account details based on the provided login identifier and displays the current balance along with the date and time of the inquiry.
   *
   * @param login The login identifier of the customer whose balance is being queried.
   */

  public void customerOperate(String login) {
    try {
      CustomerAccount account = customerAccountRepository.findByUsername(login);
      System.out.println("===Your account detail is: ");
      System.out.println("Account #" + account.getAccountid());
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedDate = now.format(formatter);
      System.out.println("Date: " + formattedDate);
      System.out.println("Balance: " + account.getBalance());
      System.out.println("Press any number to return");
      scanner.next();
    } catch (DataAccessException e) {
      System.out.println("Error accessing data: " + e.getMessage());
    }
  }
}
