package com.xiaojun;

import java.sql.*;
import java.util.Scanner;

public class IsLoginExist {
    public String islogin(String islogin)throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/atm509";
            String user = "root";
            String pwd = "123";

            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();

            String sql = "SELECT COUNT(*) FROM caccounts WHERE login = ?";

            try (
                    PreparedStatement prepst = connection.prepareStatement(sql);
            ) {
                prepst.setString(1,islogin);
                ResultSet resultset = prepst.executeQuery();

                if(resultset.next()) {
                    int count = resultset.getInt(1);
                    if(count > 0) {
                        return "y";
                    }else{
                        return "n";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
