package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for updating customer account details.
 * This class implements administrative operations that allow an admin to update various attributes of a customer's account.
 */
@Service
public class UpdateAccount implements IAdminOperate {
  @Autowired
  private final CustomerAccountRepository customerAccountRepository;
  private final DisplayAccountDetail displayAccountDetail;
  private final Scanner scanner;

  public UpdateAccount(
            Scanner scanner,
            CustomerAccountRepository customerAccountRepository,
            DisplayAccountDetail displayAccountDetail) {
    this.scanner = scanner;
    this.customerAccountRepository = customerAccountRepository;
    this.displayAccountDetail = displayAccountDetail;
  }
  /**
    * Administers the operation for updating customer accounts.
    * Continuously prompts the admin to enter account numbers to update and allows editing of specific account details.
    * The process can be exited at any time by entering 'exit'.
    */

  public void adminOperate() {
    while (true) {
      System.out.println("===You have entered update interface=== \n---Enter any number the process will continue to update");
      System.out.println("---Enter 'return' to return");
      String enter = scanner.next();
      if ("return".equalsIgnoreCase(enter)) {
        System.out.println("===return admin interface===");
        return;
      }
      System.out.println("Please enter the Account# you want to update: ");
      Long accountId = scanner.nextLong();
      CustomerAccount exist = customerAccountRepository.findByAccountid(accountId);
      if (exist != null) {
        System.out.println("Account detail is: ");
        displayAccountDetail.display(accountId);

        System.out.println("Choose the attribute you want to update");
        System.out.println("1.login name 2.pin code 3.holder name 4.status");
        System.out.println("Press 9 to exit");

        String command = scanner.next();
        switch (command) {
          case "1":
            System.out.println("Input the new login name");
            String newUserName = scanner.next();
            CustomerAccount account = customerAccountRepository.findByUsername(newUserName);
            if (account == null) {
              exist.setUsername(newUserName);
              customerAccountRepository.saveAndFlush(exist);
              System.out.println("Update Username Successfully!");
              displayAccountDetail.display(accountId);
            } else {
              System.out.println("***The login name has been exited, please try another one!");
              continue;
            }
            break;
          case "2":
            while (true) {
              System.out.println("Input the new pin code");
              String newPinCode = scanner.next();
              if (Pattern.matches("\\d+", newPinCode) && newPinCode.length() == 5) {
                exist.setPincode(newPinCode);
                customerAccountRepository.saveAndFlush(exist);
                if (exist.getPincode().equals(newPinCode)) {
                  System.out.println("Update pin code Successfully!");
                  System.out.println("The updated account details are as follows: ");
                  displayAccountDetail.display(accountId);
                  break;
                } else {
                  System.out.println("***Update pin code failed***!");
                }
              } else {
                System.out.println("***Wrong pin code format, try again***!");
              }
            }
            break;
          case "3":
            System.out.println("Input the holder name code");
            String newHolderName = scanner.next();
            exist.setName(newHolderName);
            customerAccountRepository.saveAndFlush(exist);
            if (exist.getName().equals(newHolderName)) {
              System.out.println("Update holder name Successfully!");
              System.out.println("The updated account details are as follows: ");
              displayAccountDetail.display(accountId);
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
              System.out.println("The updated account details are as follows: ");
              displayAccountDetail.display(accountId);
            } else {
              System.out.println("***Update status failed***!");
            }
            break;
          default:
            System.out.println("***Have exited***");
            return;
        }
      } else {
        System.out.println("***Non account exist***");
      }
    }
  }
}
