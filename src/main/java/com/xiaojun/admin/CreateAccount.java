package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the administrative operation for creating new customer accounts.
 * This class handles the user input necessary to gather all details required to create a new account in the system.
 */
@Service
public class CreateAccount implements IAdminOperate {

  @Autowired
  private CustomerAccountRepository customerAccountRepository;
  private final Scanner scanner;

  /**
    * Constructor for CreateAccount service with dependencies injected via Spring's @Autowired annotation.
    *
    * @param scanner The scanner object to handle user input.
    * @param customerAccountRepository The repository used for customer account operations.
    */
  public CreateAccount(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
  }

  /**
    * Implements the admin operation to create a new customer account. Prompts the administrator for account details
    * and commits them to the database if they meet validation criteria.
    */
    public void adminOperate() {
        try {
            mainInputLoop();
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }

    private void mainInputLoop() {
        while (true) {
            System.out.println("Please input the login name you want to create, or type 'exit' to quit:");
            String loginName = scanner.next();
            if ("exit".equalsIgnoreCase(loginName)) {
                System.out.println("Exiting the creation process.");
                break;
            }
            handleAccountCreation(loginName);
        }
    }

    private void handleAccountCreation(String loginName) {
        CustomerAccount exist = customerAccountRepository.findByUsername(loginName);
        if (exist == null) {
            CustomerAccount account = new CustomerAccount();
            account.setUsername(loginName);
            if (collectAccountDetails(account)) {
                customerAccountRepository.saveAndFlush(account);
                System.out.println("Account created successfully!");
            }
        } else {
            System.out.println("***The login name already exists, please try another login name***");
        }
    }

    private boolean collectAccountDetails(CustomerAccount account) {
        if (!validateAndSetPinCode(account)) {
            return false;
        }
        System.out.println("Please input Holder's name: ");
        account.setName(scanner.nextLine());

        if (!setInitialMoney(account)) {
            return false;
        }

        System.out.println("Please set customer status (active/disabled): ");
        account.setStatus(scanner.next());
        return true;
    }

    private boolean validateAndSetPinCode(CustomerAccount account) {
        while (true) {
            System.out.println("Please input customer Pin Code (5 digits): ");
            String pinCode = scanner.next();
            scanner.nextLine();  // Clear the newline left over
            if (Pattern.matches("\\d{5}", pinCode)) {
                account.setPincode(pinCode);
                return true;
            } else {
                System.out.println("***Wrong Pin Code format, please try again!***");
            }
        }
    }

    private boolean setInitialMoney(CustomerAccount account) {
        System.out.println("Please input initial money: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Type of number is wrong, please input correct number");
            scanner.next();
        }
        account.setBalance(scanner.nextDouble());
        return true;
    }
}
