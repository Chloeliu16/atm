package com.xiaojun;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Updatedeposit {
    private Scanner sc= new Scanner(System.in);
    public void updateb(String login) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

            System.out.println("Enter the cash amount to deposit: ");
            double money = sc.nextDouble();
            String sql = "SELECT account, login, pincode, holdername, balance, status FROM caccounts WHERE login = ?" ;

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,login);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                double balance = rs.getDouble("balance");
                String account = rs.getString("account");
                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String formattedDate = now.format(formatter);
                //search
                    sql = "UPDATE caccounts SET balance = ? WHERE login = ?";
                    PreparedStatement pstmt1 = connection.prepareStatement(sql);
                    balance = balance + money;
                    String sbalance = String.valueOf(balance);

                    pstmt1.setString(1, sbalance);
                    pstmt1.setString(2, login);

                    int rowsAffected = pstmt1.executeUpdate();
                    System.out.println("Cash deposit successfully.");
                    System.out.println("Account #" + account);System.out.println("Date: "+ formattedDate);
                    System.out.println("Deposit: " + money);
                    System.out.println("Balance: " + balance);
                    System.out.println("===Please press any number to return===");
                    String command = sc.next();
                    switch (command) {
                        default:
                            return;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
