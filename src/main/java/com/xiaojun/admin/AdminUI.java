package com.xiaojun.admin;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class AdminUI {
    private final IAdminOperate createAccount;
    private final IAdminOperate deleteAccount;
    private final IAdminOperate updateAccount;
    private final IAdminOperate searchAccount;
    private final Scanner scanner;
    @Autowired
    public AdminUI(Scanner scanner, IAdminOperate createAccount, IAdminOperate deleteAccount,
                   IAdminOperate updateAccount, IAdminOperate searchAccount) {
        this.scanner = scanner;
        this.createAccount = createAccount;
        this.deleteAccount = deleteAccount;
        this.updateAccount = updateAccount;
        this.searchAccount = searchAccount;
    }

    public void showAdminUI() {
        while (true) {
            System.out.println("==Administrator Interface==");
            System.out.println("===You can select one of the following options: ===");
            System.out.println("1----Create New Account");
            System.out.println("2----Delete Existing Account");
            System.out.println("3----Update Account Information");
            System.out.println("4----Search Account");
            System.out.println("5----Exit");

            try {
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        createAccount.adminOperate();
                        break;
                    case 2:
                        deleteAccount.adminOperate();
                        break;
                    case 3:
                        updateAccount.adminOperate();
                        break;
                    case 4:
                        searchAccount.adminOperate();
                        break;
                    case 5:
                        System.out.println("===You have logged out.===");
                        return;
                    default:
                        System.out.println("***Your operation is incorrect, please select again.***");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
