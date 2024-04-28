package com.xiaojun;

import com.xiaojun.login.ATM;
import com.xiaojun.admin.AdminLogin;
import com.xiaojun.customer.CustomerLogin;
import com.xiaojun.login.ILogin;

import java.sql.SQLException;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("===WELCOME, Please choose your user type===");
                System.out.println("1.Customer");
                System.out.println("2.Administrator");
                System.out.println("Please select: ");
                String command = sc.next();

                ILogin admin = ctx.getBean("adminLogin", ILogin.class);
                ILogin customer = ctx.getBean("customerLogin", ILogin.class);
                ATM atm = ctx.getBean(ATM.class);

                switch (command) {
                    case "1":
                        // Customer login
                        atm.login(customer);
                        break;
                    case "2":
                        // Admin login
                        atm.login(admin);
                        break;
                    default:
                        System.out.println("***Your enter is wrong***!");
                        break;
                }
            }
        };
    }
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
