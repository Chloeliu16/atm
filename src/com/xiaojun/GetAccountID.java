package com.xiaojun;

import java.sql.*;

public class GetAccountID {
    public static String getacc(String aID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/atm509";
            String user = "root";
            String pwd = "123";

            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();

            String sql = "SELECT COUNT(*) FROM caccounts WHERE account = ?";

            try (
                    PreparedStatement prepst = connection.prepareStatement(sql);
                    ) {
                prepst.setString(1,aID);
                ResultSet resultset = prepst.executeQuery();

                if(resultset.next()) {
                    int count = resultset.getInt(1);
                    if(count > 0) {
                        return "y";
                    }else{
                        return null;
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
