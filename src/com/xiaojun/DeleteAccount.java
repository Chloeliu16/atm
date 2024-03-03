package com.xiaojun;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DeleteAccount {

        private Scanner sc= new Scanner(System.in);

        public void delete() throws /**ClassNotFoundException, */SQLException {
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

                System.out.println("==Delete Interface==");

                System.out.print("Enter the account number to which you want to delete: ");
                String accountID = sc.next();

                String sql = "SELECT login FROM caccounts WHERE account = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);

                pstmt.setString(1, accountID);

                ResultSet rs = pstmt.executeQuery();
                Statement stmt = connection.createStatement();

                while (rs.next()) {
                    String login = rs.getString("login");
                    System.out.println("***You wish to delete the account held by "+ login + ". \nIf this information is correct, please re-enter the account number:***");
                    while (true) {
                        String reaccountID = sc.next();
                        if(accountID.equals(reaccountID)) {
                            GetAccountID getAccountID = new GetAccountID();
                            if (getAccountID.getacc(accountID).equals("y")){
                                try (connection) {
                                    sql = "DELETE FROM caccounts WHERE account = ?";
                                    PreparedStatement pstmt1 = connection.prepareStatement(sql);
                                    pstmt1.setString(1, accountID);
                                    int rowsAffected = pstmt1.executeUpdate();

                                    System.out.println("Delete account#" + accountID + " successfully!");
                                    System.out.print("===Press any number to exit===");
                                    String command = sc.next();
                                    switch (command) {
                                        default:
                                            return;
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            System.out.println("The account# doesn't match, try again");
                        }
                    }
                }
                System.out.println("The account# doesn't exit, try again");
            }
        }
}
