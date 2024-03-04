package com.xiaojun;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UpdateAccount {
    private Scanner sc= new Scanner(System.in);
    String url = "jdbc:mysql://localhost:3306/atm509";
    String user = "root";
    String pwd = "123";
    public void update() throws SQLException {
        System.out.println("Please enter the Account# you want to update: ");
        String accnum = sc.next();
        String url = "jdbc:mysql://localhost:3306/atm509";
        String user = "root";
        String pwd = "123";
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {

            Statement stmt = connection.createStatement();

            String sql = "SELECT account, login, pincode, holdername, balance, status FROM caccounts WHERE account =" + accnum;

            ResultSet rs = stmt.executeQuery(sql);

            while (true) {

                System.out.println("Choose the atttibute you want to update");
                System.out.println("1.login 2.pincode 3.holdername 4.status");
                System.out.println("Press 9 to exit");
                int command = sc.nextInt();
                switch (command) {
                    case 1:
                        updatelogin(accnum);
                        break;
                    case 2:
                        updatepincode(accnum);
                        break;
                    case 3:
                        updateholdername(accnum);
                        break;
                    case 4:
                        updatepstatus(accnum);
                        break;
                    default:
                        return;

                }
                ResultSet rs1 = stmt.executeQuery(sql);
                while (rs1.next()) {
                String aaccount = rs1.getString("account");
                String login = rs1.getString("login");
                String pincode = rs1.getString("pincode");
                String holdername = rs1.getString("holdername");
                double balance = rs1.getDouble("balance");
                String status = rs1.getString("status");

                System.out.println("Account detail after update is: ");
                System.out.println("Account# " + aaccount);
                System.out.println("login: " + login);
                System.out.println("pincode " + pincode);
                System.out.println("holdername: " + holdername);
                System.out.println("balance: " + balance);
                System.out.println("Status: " + status);}
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updatelogin(String accnum){

        try (Connection connection = DriverManager.getConnection(url, user, pwd)){
            while (true) {
                System.out.println("Please enter new login: ");
                String newname = sc.next();
                IsLoginExist isLoginExist = new IsLoginExist();
                if(isLoginExist.islogin(newname).equals("n")) {
                    String sql = "UPDATE caccounts SET login = ? WHERE account = ?";
                    PreparedStatement pstmt = connection.prepareStatement(sql);

                    pstmt.setString(1, newname);
                    pstmt.setString(2, accnum);

                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Login has been updated to: " + newname);
                    return;
                }else{
                    System.out.println("***The login name has been existed, please try again***");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updatepincode(String accnum){

        try (Connection connection = DriverManager.getConnection(url, user, pwd)){
            while (true) {
                System.out.println("Please enter new pincode: ");
                //String newpincode = sc.next();
                String newpinCode = sc.next();
                if (Pattern.matches("\\d+", newpinCode)) {
                    if (newpinCode.length() == 5) {
                        String sql = "UPDATE caccounts SET pincode = ? WHERE account = ?";
                        PreparedStatement pstmt = connection.prepareStatement(sql);

                        pstmt.setString(1, newpinCode);
                        pstmt.setString(2, accnum);

                        int rowsAffected = pstmt.executeUpdate();
                        System.out.println("PinCode has been updated to: " + newpinCode);
                        break;
                    } else {
                        System.out.println("***Wrong Pin Code format, please try again!***");
                    }
                }else{
                    System.out.println("***Wrong Pin Code format, please try again!***");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateholdername(String accnum){

        try (Connection connection = DriverManager.getConnection(url, user, pwd)){
            sc.nextLine();
            System.out.println("Please enter new holdername: ");
            String newholdername = sc.nextLine();

            String sql = "UPDATE caccounts SET holdername = ? WHERE account = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newholdername);
            pstmt.setString(2,accnum);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Hodername has been updated to: "+ newholdername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updatepstatus(String accnum){

        try (Connection connection = DriverManager.getConnection(url, user, pwd)){
            System.out.println("Please enter new status: ");
            String newstatus = sc.next();

            String sql = "UPDATE caccounts SET status = ? WHERE account = ?";
            //
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newstatus);
            pstmt.setString(2,accnum);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Status has been updated to: "+ newstatus);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
