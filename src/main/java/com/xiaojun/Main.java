package com.xiaojun;

import com.xiaojun.login.ATM;
import com.xiaojun.login.ILogin;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Main application class that bootstraps a Spring Boot application.
 * This class defines the main entry point of the application and configures Spring context with specific beans.
 */
@SpringBootApplication
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  /**
   * Bean definition for a CommandLineRunner that includes the user interface for login selection.
   * This runner allows the user to choose between different user types for interaction.
   * @param ctx Spring's application context, providing access to beans.
   * @return a CommandLineRunner with embedded operational logic.
   */
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
  /**
   * Bean definition for creating a Scanner instance.
   * This bean is used across the application to read input from the console.
   * @return a new Scanner instance configured to read from the standard input stream.
   */

  @Bean
  public Scanner scanner() {
    return new Scanner(System.in);
  }
}
