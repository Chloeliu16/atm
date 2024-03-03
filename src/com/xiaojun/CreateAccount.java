package com.xiaojun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CreateAccount {
    private Scanner sc= new Scanner(System.in);
    public void create() throws SQLException {
        while (true) {
            System.out.println("Please input login name: ");
            String loginname = sc.next();
            while (true) {
                System.out.print("Please input customer Pin Code: ");
                String pinCode = sc.next();
                if (Pattern.matches("\\d+", pinCode)) {
                    if (pinCode.length() == 5) {
                        System.out.print("Please input Holder's name: ");
                        String holdersName = sc.next();

                        System.out.print("Please input initial money: ");
                        double money = sc.nextDouble();

                        System.out.print("Please set customer status(active/disabled): ");
                        String status = sc.next();

                        String newAccountID = createRandomID();

                        String sql = "INSERT INTO caccounts (account,login,pincode,holdername,balance,status) VALUES(" + newAccountID + ", \"" + loginname + "\"," + pinCode + ",\"" + holdersName + "\"," + String.valueOf(money) + ",\"" + status + "\")";

                        //Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/atm509";
                        String user = "root";
                        String pwd = "123";
                        Connection connection = DriverManager.getConnection(url, user, pwd);
                        Statement statement = connection.createStatement();
                        if (statement.executeUpdate(sql) >= 1) {
                            connection.close();
                            System.out.println("Account#"+ newAccountID+" has been created successfully!");
                            System.out.println("===You can press any number to return admin interface===");
                            String command = sc.next();
                            switch (command) {
                                default:
                                    return;
                            }
                        } else {
                            System.out.println("***Fail to Create!***");
                        }
                    }else{
                        System.out.println("***Wrong Pin Code format, please try again!***");
                        break;
                    }
                }else{
                    System.out.println("***Wrong Pin Code format, please try again!***");
                    break;
                }
            }
        }
    }
            private String createRandomID () {

                String account_ID = "";

                while (true) {
                    Random r = new Random();
                    for (int i = 0; i < 2; i++) {
                        int data = r.nextInt(10);
                        account_ID += data;
                    }

                    GetAccountID getAccountID = new GetAccountID();
                    String result = getAccountID.getacc(account_ID);
                    if (result == null) {
                        return account_ID;
                    } else {
                        System.out.print("***This account has been existed!***");
                    }
                }

            }
 }
