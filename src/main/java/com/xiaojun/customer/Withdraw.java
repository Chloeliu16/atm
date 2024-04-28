package com.xiaojun.customer;

import com.xiaojun.modle.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class Withdraw implements ICustomerOperate {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    private final Scanner scanner;
    public Withdraw(Scanner scanner, CustomerAccountRepository customerAccountRepository) {
        this.scanner = scanner;
        this.customerAccountRepository = customerAccountRepository;
    }
    public void customerOperate(String login){

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
                    System.out.println("Date: "+ formattedDate);
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
