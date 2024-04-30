package com.xiaojun;

import com.xiaojun.login.ATM;
import com.xiaojun.login.ILogin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MainTest {

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private Scanner scanner;
    @Mock
    private ILogin adminLogin;
    @Mock
    private ILogin customerLogin;
    @Mock
    private ATM atm;

    @InjectMocks
    private Main main;

    private CommandLineRunner runner;

    @BeforeEach
    void setup() {
        // Prepare the CommandLineRunner
        runner = main.commandLineRunner(applicationContext);

        // Configure ApplicationContext to return the mocked beans
        given(applicationContext.getBean("adminLogin", ILogin.class)).willReturn(adminLogin);
        given(applicationContext.getBean("customerLogin", ILogin.class)).willReturn(customerLogin);
        given(applicationContext.getBean(ATM.class)).willReturn(atm);
        given(applicationContext.getBean(Scanner.class)).willReturn(scanner);
    }

    @Test
    void testCustomerLoginSelection() throws Exception {
        // Simulate the user input for customer login
        when(scanner.next()).thenReturn("1");

        // Execute the command line runner
        runner.run(new String[]{});

        // Verify that customer login was invoked
        verify(atm).login(customerLogin);
    }

    @Test
    void testAdminLoginSelection() throws Exception {
        // Simulate the user input for admin login
        when(scanner.next()).thenReturn("2");

        // Execute the command line runner
        runner.run(new String[]{});

        // Verify that admin login was invoked
        verify(atm).login(adminLogin);
    }

    @Test
    void testInvalidUserTypeSelection() throws Exception {
        // Simulate the user input for an invalid option
        when(scanner.next()).thenReturn("3");

        // Execute the command line runner
        runner.run(new String[]{});

        // Verify that no login method was called
        verify(atm, never()).login(any(ILogin.class));
    }
}
