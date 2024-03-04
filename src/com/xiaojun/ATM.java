package com.xiaojun;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ATM {
    private Scanner sc= new Scanner(System.in);

    public void start() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("===WELCOME, Please choose your user type===");
            System.out.println("1.Customer");
            System.out.println("2.Administrator");
            System.out.println("Please select: ");
            String command = sc.next();
            switch (command) {
                case "1":
                    //customer login
                    CustomerAccessDB cusDB = new CustomerAccessDB();
                    cusDB.access();
                    break;
                case "2":
                    //admin login
                    AdminAccessDB accessdb = new AdminAccessDB();
                    accessdb.access();
                    break;
                default:
                    System.out.println("***Your enter is wrong***!");
                    break;
            }
        }
    }
}
