package com.xiaojun.admin;


import com.xiaojun.modle.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UpdateAccount implements IAdminOperate {
    @Autowired
    private final CustomerAccountRepository customerAccountRepository;
    private final Scanner scanner;
    private UpdateAccount(
            Scanner scanner,
            CustomerAccountRepository customerAccountRepository)
    {
        this.scanner = scanner;
        this.customerAccountRepository = customerAccountRepository;
    }

    public void adminOperate() {
        while (true) {
            System.out.println("You have entered update interface. Enter any number the process will continue");
            System.out.println("***Enter 'exit' to exit***");
            String enter = scanner.next();
            if ("exit".equalsIgnoreCase(enter)) {
                System.out.println("Exiting login process.");
                return;
            }
            System.out.println("Please enter the Account# you want to update: ");
            Long accountId = scanner.nextLong();
            CustomerAccount exist = customerAccountRepository.findByAccountid(accountId);
            if (exist != null) {
                System.out.println("Account detail is: ");
                System.out.println("Account# " + exist.getAccountid());
                System.out.println("login: " + exist.getUsername());
                System.out.println("pinCode: " + exist.getPincode());
                System.out.println("holderName: " + exist.getName());
                System.out.println("balance: " + exist.getBalance());
                System.out.println("Status: " + exist.getStatus());

                System.out.println("Choose the attribute you want to update");
                System.out.println("1.login name 2.pin code 3.holder name 4.status");
                System.out.println("Press 9 to exit");

                String command = scanner.next();
                switch (command) {
                    case "1":
                        System.out.println("Input the new login name");
                        String newUserName = scanner.next();
                        exist.setUsername(newUserName);
                        customerAccountRepository.saveAndFlush(exist);
                        if (exist.getUsername().equals(newUserName)) {
                            System.out.println("Update Username Successfully!");
                        } else {
                            System.out.println("***Update Username failed***!");
                        }
                        break;
                    case "2":
                        System.out.println("Input the new pin code");
                        String newPinCode = scanner.next();
                        exist.setPincode(newPinCode);
                        customerAccountRepository.saveAndFlush(exist);
                        if (exist.getPincode().equals(newPinCode)) {
                            System.out.println("Update pin code Successfully!");
                        } else {
                            System.out.println("***Update pin code failed***!");
                        }
                        break;
                    case "3":
                        System.out.println("Input the holder name code");
                        String newHolderName = scanner.next();
                        exist.setName(newHolderName);
                        customerAccountRepository.saveAndFlush(exist);
                        if (exist.getName().equals(newHolderName)) {
                            System.out.println("Update holder name Successfully!");
                        } else {
                            System.out.println("***Update holder name failed***!");
                        }
                        break;
                    case "4":
                        System.out.println("Input the status code");
                        String newStatus = scanner.next();
                        exist.setStatus(newStatus);
                        customerAccountRepository.saveAndFlush(exist);
                        if (exist.getStatus().equals(newStatus)) {
                            System.out.println("Update status Successfully!");
                        } else {
                            System.out.println("***Update status failed***!");
                        }
                        break;
                    default:
                        System.out.println("***Have returned***");
                        return;
                }
            } else {
                System.out.println("***Non account exist***");
            }
        }
    }
}