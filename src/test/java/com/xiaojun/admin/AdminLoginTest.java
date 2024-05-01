package com.xiaojun.admin;

import com.xiaojun.model.AdminAccount;
import com.xiaojun.repository.AdminAccountRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
public class AdminLoginTest {

    @Mock
    private Scanner scanner;
    @Mock
    private AdminUI adminUI;
    @Mock
    private AdminAccountRepository adminAccountRepository;

    @InjectMocks
    private AdminLogin adminLogin;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture output
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut); // Restore the original System.out after the test
    }
    @Test
    public void testExitLoginProcess() {
        when(scanner.next()).thenReturn("exit");
        adminLogin.accountLogin();
        verify(scanner, times(1)).next();
        verifyNoInteractions(adminUI); // Ensures admin UI is never interacted with
    }

    @Test
    public void testSuccessfulLogin() {
        when(scanner.next()).thenReturn("admin", "1234"); // username and password
        when(adminAccountRepository.findByLoginAndPincode("admin", "1234")).thenReturn(new AdminAccount());

        adminLogin.accountLogin();

        verify(adminUI, times(1)).showAdminUI();
    }
    @Test
    public void testFailedLogin() {
        when(scanner.next()).thenReturn("admin", "wrongPassword", "exit");
        when(adminAccountRepository.findByLoginAndPincode("admin", "wrongPassword")).thenReturn(null);

        adminLogin.accountLogin();

        verify(adminUI, never()).showAdminUI();
        verify(scanner, atLeast(2)).next(); // Checks that user input was prompted at least twice
    }
    @Test
    public void testDataAccessException() {

        when(scanner.next()).thenReturn("admin", "1234");
        when(adminAccountRepository.findByLoginAndPincode("admin", "1234"))
                .thenThrow(new RuntimeException("Database error"));

        try {
            adminLogin.accountLogin();
        } catch (RuntimeException e) {
            fail("Exception was not expected to propagate to the test: " + e.getMessage());
        }

        assertTrue(outContent.toString().contains("Error accessing data: Database error"));
    }

}
