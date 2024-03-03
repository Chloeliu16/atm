package com.xiaojun;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DisplayBL {
    private Scanner sc= new Scanner(System.in);
    public void display(String login)throws SQLException{
            String url = "jdbc:mysql://localhost:3306/atm509";
            String user = "root";
            String pwd = "123";
            try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

                String sql = "SELECT account, balance FROM caccounts WHERE login = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);

                pstmt.setString(1, login);

                ResultSet rs = pstmt.executeQuery();
                Statement stmt = connection.createStatement();

                while (rs.next()) {
                    String account = rs.getString("account");
                    double balance = rs.getDouble("balance");
                    LocalDate now = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    String formattedDate = now.format(formatter);
                    System.out.println("Account #" + account);
                    System.out.println("Date: "+ formattedDate);
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
