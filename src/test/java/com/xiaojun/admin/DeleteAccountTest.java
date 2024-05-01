package com.xiaojun.admin;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.NoSuchElementException;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

public class DeleteAccountTest {
    @Mock
    private CustomerAccountRepository customerAccountRepository;
    @Mock
    private Scanner scanner;

    @InjectMocks
    private DeleteAccount deleteAccount;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("testLogin\n9\n".getBytes());

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        String input = "testLogin\n9\n";
        deleteAccount = new DeleteAccount(scanner, customerAccountRepository);
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    void testDeleteAccountSuccess() {

        String loginName = "user123";
        CustomerAccount exist = new CustomerAccount();
        exist.setUsername(loginName);

        when(scanner.next()).thenReturn(loginName, "9", "any number");

        when(customerAccountRepository.findByUsername(loginName)).thenReturn(exist);

        // Act
        deleteAccount.adminOperate();

        // Assert
        verify(customerAccountRepository).findByUsername(loginName);
        verify(customerAccountRepository).delete(exist);
        verify(scanner, times(3)).next(); // Ensure there are three inputs as per method logic
    }
    @Test
    void testAccountNotFound() {
        String loginName = "nonexistent";
        when(scanner.next()).thenReturn(loginName, "0");
        when(customerAccountRepository.findByUsername(loginName)).thenReturn(null);

        deleteAccount.adminOperate();

        verify(customerAccountRepository).findByUsername(loginName);
        verify(customerAccountRepository, never()).delete(any(CustomerAccount.class));
        verify(scanner, times(2)).next();
    }


    @Test
    void testDataAccessExceptionHandling() {
        // Arrange
        String loginName = "user123";
        when(scanner.next()).thenReturn(loginName);
        when(customerAccountRepository.findByUsername(loginName)).thenThrow(new DataAccessException("Database error") {});

        deleteAccount.adminOperate();

        assertTrue(outContent.toString().contains("Error accessing data: Database error"));
    }

    @Test
    void testExitWithoutDeletion() {

        String loginName = "user123";
        CustomerAccount exist = new CustomerAccount();
        exist.setUsername(loginName);

        when(scanner.next()).thenReturn(loginName, "9", "0");
        when(customerAccountRepository.findByUsername(loginName)).thenReturn(exist);

        deleteAccount.adminOperate();

        verify(customerAccountRepository).delete(exist);
        assertTrue(outContent.toString().contains("Press any number to exit"));
    }
    @Test
    void testInvalidCommandInput() {

        String loginName = "user123";
        CustomerAccount exist = new CustomerAccount();
        exist.setUsername(loginName);

        when(scanner.next()).thenReturn(loginName, "invalid_command", "0");
        when(customerAccountRepository.findByUsername(loginName)).thenReturn(exist);

        deleteAccount.adminOperate();

        verify(customerAccountRepository).findByUsername(loginName);
        verify(customerAccountRepository, never()).delete(any(CustomerAccount.class));
        assertTrue(outContent.toString().contains("***Invalid input, please try again***"));
        assertTrue(outContent.toString().contains("If you want to cancel delete operation, press 0 to back"));
    }
}
