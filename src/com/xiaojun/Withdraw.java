package com.xiaojun;

import java.sql.*;
import java.util.Scanner;

public class Withdraw {
    public Scanner sc= new Scanner(System.in);
    public void withdraw(String login){
        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

            String sql = "SELECT balance FROM caccounts WHERE login = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();
            while (true) {
                System.out.println("Enter the withdraw amount: ");
                Double money = sc.nextDouble();

                while (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if(balance >= money) {
                        WUpdateBalance wupdateBalance = new WUpdateBalance();
                        wupdateBalance.updateb(login,money);
                        return;
                    }else{
                        System.out.println("Your balance is not enough, \nYour current balance is: "+ balance + "please try less amount***");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
