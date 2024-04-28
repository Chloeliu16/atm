package com.xiaojun.customer;

import com.xiaojun.login.ILogin;
import com.xiaojun.modle.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

@Component
public class CustomerLogin implements ILogin {
    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerUI customerUI;
    private final Scanner scanner;
    public CustomerLogin(
            Scanner scanner,
            CustomerAccountRepository customerAccountRepository,
            CustomerUI customerUI)
    {
        this.scanner = scanner;
        this.customerAccountRepository = customerAccountRepository;
        this.customerUI = customerUI;
    }
    @Override
    public void accountLogin() {
        System.out.println("Connect system successfully!");

        while (true) {
            System.out.println("Enter login: ");
            String login = scanner.next();
            System.out.println("Enter Pin code: ");
            String pinCode = scanner.next();
            CustomerAccount customer = customerAccountRepository.findByUsernameAndPincode(login,pinCode);
            if (customer != null) {
                customerUI.showCustomerUI(login);
                break;
            } else{
                System.out.println("***Your info is wrong, please try again!***");
            }
        }
    }
}
