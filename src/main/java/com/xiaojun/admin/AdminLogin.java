package com.xiaojun.admin;

import com.xiaojun.login.ILogin;

import java.sql.*;
import java.util.Scanner;

import com.xiaojun.modle.AdminAccount;
import com.xiaojun.repository.AdminAccountRepository;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AdminLogin implements ILogin {
    private final AdminUI adminUI;
    private final Scanner scanner;
    private final AdminAccountRepository adminAccountRepository;

    @Autowired
    public AdminLogin(Scanner scanner, AdminUI adminUI, AdminAccountRepository adminAccountRepository) {
        this.scanner = scanner;
        this.adminUI = adminUI;
        this.adminAccountRepository = adminAccountRepository;
    }

    public void accountLogin() {
        try {
            System.out.println("Connect successfully！");

            while (true) {
                System.out.println("Enter username (type 'exit' to quit): ");
                String username = scanner.next();
                if ("exit".equalsIgnoreCase(username)) {
                    System.out.println("Exiting login process.");
                    break;
                }
                System.out.println("Enter password: ");
                String password = scanner.next();
                AdminAccount exist = adminAccountRepository.findByLoginAndPincode(username, password);
                if (exist != null) {
                    adminUI.showAdminUI();
                    break;
                } else {
                    System.out.println("***Wrong info, try again!***");
                }
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }
}