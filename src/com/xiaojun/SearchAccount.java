package com.xiaojun;

import java.sql.*;
import java.util.Scanner;

public class SearchAccount {
    private Scanner sc= new Scanner(System.in);
    public void search() throws SQLException {
        while(true) {
            System.out.println("Please enter the Account# you want to search: ");
            String accnum = sc.next();
            String url = "jdbc:mysql://localhost:3306/atm509";
            String user = "root";
            String pwd = "123";
            try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

                Statement stmt = connection.createStatement();

                String sql = "SELECT account, login, pincode, holdername, balance, status FROM caccounts WHERE account = " + accnum;

                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String aaccount = rs.getString("account");
                    String login = rs.getString("login");
                    String pincode = rs.getString("pincode");
                    String holdername = rs.getString("holdername");
                    double balance = rs.getDouble("balance");
                    String status = rs.getString("status");

                    System.out.println("Account detail is: ");
                    System.out.println("Account# " + aaccount);
                    System.out.println("login: " + login);
                    System.out.println("pincode " + pincode);
                    System.out.println("holdername: " + holdername);
                    System.out.println("balance: " + balance);
                    System.out.println("Status " + status);
                    System.out.println("===Press any number to exit===");
                    String command = sc.next();
                    switch (command) {
                        default:
                            return;
                    }
                }
                System.out.println("***The account doesn't exit***");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
