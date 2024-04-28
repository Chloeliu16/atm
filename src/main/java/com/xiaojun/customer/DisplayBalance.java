package com.xiaojun.customer;

import com.xiaojun.modle.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class DisplayBalance implements ICustomerOperate {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    private final Scanner scanner;
    public DisplayBalance(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
        this.scanner = scanner;
        this.customerAccountRepository = customerAccountRepository;
    }
    public void customerOperate(String login){
        try {
            CustomerAccount account = customerAccountRepository.findByUsername(login);
            System.out.println("===Your account detail is: ");
            System.out.println("Account #" + account.getAccountid());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            System.out.println("Date: "+ formattedDate);
            System.out.println("Balance: " + account.getBalance());
            System.out.println("===Please press any number to return===");
//            String command = sc.next();
//            return;
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }
}
