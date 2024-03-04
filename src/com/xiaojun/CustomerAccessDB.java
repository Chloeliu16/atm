package com.xiaojun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerAccessDB {

    public Scanner sc= new Scanner(System.in);

    public void access() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";

        Connection connection = DriverManager.getConnection(url, user, pwd);

        if(connection == null) {
            System.out.println("***Connect system unsuccessfully***");
        }else {
            System.out.println("Connect system successfully!");

            while (true) {
                System.out.println("Enter login: ");
                String login = sc.next();
                System.out.println("Enter Pin code: ");
                String pincode = sc.next();
                IsCustomer isCustomer = new IsCustomer();
                if (isCustomer.validate(login, pincode)) {
                    showCustomerInterface(login);
                    break;
                }else{
                    System.out.println("***Your info is wrong, please try again!***");
                    break;
                }
            }
        }
    }
    private void showCustomerInterface(String login) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("===Welcome to Customer Interface===");
            System.out.println("===You can select one of the following options: ===");
            System.out.println("1----Withdraw Cash");
            System.out.println("3----Deposit Cash");
            System.out.println("4----Display Balance");
            System.out.println("5----Exit");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //withdraw
                    Withdraw withdraw_ =new Withdraw();
                    withdraw_.withdraw(login);
                    break;
                case 3:
                    //deposit
                    Updatedeposit updatedeposit = new Updatedeposit();
                    updatedeposit.updateb(login);
                    break;
                case 4:
                    //search for account
                    DisplayBL displayBL =new DisplayBL();
                    displayBL.display(login);
                    break;
                case 5:
                    //exit
                    System.out.println("You have logged out.");
                    return;
                default:
                    System.out.println("***Your operation is incorrect, please select again.***");
                    break;
            }
        }
    }
}
