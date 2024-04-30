package com.xiaojun;

import com.xiaojun.login.ATM;
import com.xiaojun.login.ILogin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class MainTest {

    @Mock
    private ApplicationContext ctx;
    @Mock
    private ILogin adminLogin;
    @Mock
    private ILogin customerLogin;
    @Mock
    private ATM atm;

    @InjectMocks
    private Main main;

    @Test
    public void testCommandLineRunner_CustomerLogin() throws Exception {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Mockito.lenient().when(ctx.getBean("customerLogin", ILogin.class)).thenReturn(customerLogin);
        Mockito.lenient().when(ctx.getBean(ATM.class)).thenReturn(atm);

        CommandLineRunner runner = main.commandLineRunner(ctx);
        runner.run(new String[]{});

        verify(atm).login(customerLogin);
        assertTrue(outContent.toString().contains("===WELCOME, Please choose your user type==="));

        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testCommandLineRunner_AdminLogin() throws Exception {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Mockito.lenient().when(ctx.getBean("adminLogin", ILogin.class)).thenReturn(adminLogin);
        Mockito.lenient().when(ctx.getBean(ATM.class)).thenReturn(atm);

        CommandLineRunner runner = main.commandLineRunner(ctx);
        runner.run(new String[]{});

        verify(atm).login(adminLogin); // Ensuring the login method is called with the adminLogin
        assertTrue(outContent.toString().contains("===WELCOME, Please choose your user type==="));

        assertTrue(outContent.toString().contains("Please select: "));
        assertFalse(outContent.toString().contains("***Your enter is wrong***!")); // This line checks that the incorrect input message is not displayed

        System.setIn(System.in);
        System.setOut(System.out);
    }

}
