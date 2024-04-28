package com.xiaojun.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class CustomerUI {
    @Autowired
    private final ICustomerOperate displayBalance;

    private final ICustomerOperate updateDeposit;

    private final ICustomerOperate withdraw;

    private final Scanner scanner;

    public CustomerUI(
            Scanner scanner,
            ICustomerOperate displayBalance,
            ICustomerOperate updateDeposit,
            ICustomerOperate withdraw)
    {
        this.scanner = scanner;
        this.displayBalance = displayBalance;
        this.updateDeposit = updateDeposit;
        this.withdraw = withdraw;
    }

    public void showCustomerUI(String login)  {
        while (true) {
            System.out.println("===Welcome to Customer Interface===");
            System.out.println("===You can select one of the following options: ===");
            System.out.println("1----Withdraw Cash");
            System.out.println("3----Deposit Cash");
            System.out.println("4----Display Balance");
            System.out.println("5----Exit");

            try {
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        //withdraw
                        withdraw.customerOperate(login);
                        break;
                    case 3:
                        //deposit
                        updateDeposit.customerOperate(login);
                        break;
                    case 4:
                        //search for account
                        displayBalance.customerOperate(login);
                        break;
                    case 5:
                        //exit
                        System.out.println("You have logged out.");
                        return;
                    default:
                        System.out.println("***Your operation is incorrect, please select again.***");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
