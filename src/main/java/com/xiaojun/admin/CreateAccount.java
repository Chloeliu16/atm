package com.xiaojun.admin;

import com.xiaojun.modle.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Pattern;

@Service
public class CreateAccount implements IAdminOperate {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    private final Scanner scanner;
    public CreateAccount(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
        this.scanner = scanner;
        this.customerAccountRepository = customerAccountRepository;
    }
    public void adminOperate() {
        try {
            while (true) {
                System.out.println("Please input the login name you want to create, or type 'exit' to quit:");
                String loginName = scanner.next();
                if ("exit".equalsIgnoreCase(loginName)) {
                    System.out.println("Exiting the creation process.");
                    break;
                }

                CustomerAccount exist = customerAccountRepository.findByUsername(loginName);
                if (exist == null) {
                    CustomerAccount account = new CustomerAccount();
                    account.setUsername(loginName);
                    System.out.println("Please input customer Pin Code (5 digits): ");
                    String pinCode = scanner.next();
                    scanner.nextLine();  // Clear the newline left over
                    if (Pattern.matches("\\d+", pinCode) && pinCode.length() == 5) {
                        account.setPincode(pinCode);
                        System.out.println("Please input Holder's name: ");
                        String holderName = scanner.nextLine();
                        account.setName(holderName);
                        System.out.println("Please input initial money: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Type of number is wrong, please input correct number");
                            scanner.next();
                        }
                        double value = scanner.nextDouble();
                        account.setBalance(value);
                        System.out.println("Please set customer status (active/disabled): ");
                        account.setStatus(scanner.next());
                        customerAccountRepository.saveAndFlush(account);
                        System.out.println("Account created successfully!");
                        break;
                    } else {
                        System.out.println("***Wrong Pin Code format, please try again!***");
                    }
                } else {
                    System.out.println("***The login name already exists, please try another login name***");
                }
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }
}