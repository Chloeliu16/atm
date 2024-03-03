package com.xiaojun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IsAdmin {
        public static boolean validate(String username, String password) {
            boolean status = false;
            try {

                try (Connection connection = DriverManager.getConnection(
                       "jdbc:mysql://localhost:3306/atm509", "root", "123")) {


                    String query = "SELECT * FROM aaccounts WHERE username = ? AND password = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);


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
