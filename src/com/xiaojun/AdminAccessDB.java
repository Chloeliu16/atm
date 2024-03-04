package com.xiaojun;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class AdminAccessDB {
    private Scanner sc= new Scanner(System.in);
    public void access() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";

        Connection connection = DriverManager.getConnection(url, user, pwd);

        if(connection == null) {
            System.out.println("***Connect unsuccessfully***");
        }else {
            System.out.println("Connect successfully！");
            Statement statement = connection.createStatement();
            while (true) {
                System.out.println("Enter username: ");
                String username = sc.next();
                System.out.println("Enter password: ");
                String password = sc.next();
                IsAdmin isAdmin = new IsAdmin();
                if (isAdmin.validate(username, password)) {
                    showAdminInterface();
                    return;

                }else{
                        System.out.println("***Wrong info, try again!***");
                        break;
                }
            }
        }
    }

    private void showAdminInterface() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("==Administrator Interface==");
            System.out.println("===You can select one of the following options: ===");
            System.out.println("1----Create New Account");
            System.out.println("2----Delete Existing Account");
            System.out.println("3----Update Account Information");
            System.out.println("4----Search Account");
            System.out.println("6----Exit");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //create new account
                    CreateAccount createAccount = new CreateAccount();
                    createAccount.create();
                    break;
                case 2:
                    //delete exsiting account
                    DeleteAccount deleteAccount = new DeleteAccount();
                    deleteAccount.delete();
                    break;
                case 3:
                    //Update account information
                    UpdateAccount updateAccount = new UpdateAccount();
                    updateAccount.update();
                    break;
                case 4:
                    //search for account
                    SearchAccount searchAccount = new SearchAccount();
                    searchAccount.search();
                    break;
                case 6:
                    //exit
                    System.out.println("===You have logged out.===");
                    return;
                default:
                    System.out.println("***Your operation is incorrect, please select again.***");
                    break;
            }
        }

    }
}
