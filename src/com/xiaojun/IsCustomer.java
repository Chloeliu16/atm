package com.xiaojun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IsCustomer {
    public static boolean validate(String login, String pincode) {
        boolean status = false;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atm509", "root", "123")) {

                String query = "SELECT * FROM caccounts WHERE login = ? AND pincode = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, pincode);


                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        status = resultSet.next();
                    }
                }
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
}
