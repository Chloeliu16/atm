package com.xiaojun.customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WUpdateBalance {
    private Scanner sc= new Scanner(System.in);
    public void updateb(String login,double money) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

            String sql = "SELECT account, login, pincode, holdername, balance, status FROM caccounts WHERE login = ?" ;

            PreparedStatement stmt1 = connection.prepareStatement(sql);
            stmt1.setString(1, login);
            ResultSet rs = stmt1.executeQuery();

            while (true) {

                while (rs.next()) {
                    String account = rs.getString("account");
                    LocalDate now = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    String formattedDate = now.format(formatter);
                    double balance = rs.getDouble("balance");

                    balance = balance - money;
                    sql = "UPDATE caccounts SET balance = ? WHERE login = ?";
                    PreparedStatement pstmt1 = connection.prepareStatement(sql);
                    String sbalance = String.valueOf(balance);

                    pstmt1.setString(1, sbalance);
                    pstmt1.setString(2, login);

                    int rowsAffected = pstmt1.executeUpdate();
                    System.out.println("Cash Successfully Withdrawn!");
                    System.out.println("Account #" + account);
                    System.out.println("Date: "+ formattedDate);
                    System.out.println("Withdrawn: " + money);
                    System.out.println("Balance: " + balance);
                    System.out.println("===Please press any number to return===");
                    String command = sc.next();
                    switch (command) {
                        default:
                            return;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
