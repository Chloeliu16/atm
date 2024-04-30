package com.xiaojun.admin;

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
        // Provide both the login name and confirmation number in the input
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
        // Arrange
        String loginName = "user123";
        CustomerAccount exist = new CustomerAccount();
        exist.setUsername(loginName);

        // Setting up the mock to return values for each call to scanner.next()
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
        // Arrange
        String loginName = "nonexistent";
        when(scanner.next()).thenReturn(loginName, "0");
        when(customerAccountRepository.findByUsername(loginName)).thenReturn(null);

        // Act
        deleteAccount.adminOperate();

        // Assert
        verify(customerAccountRepository).findByUsername(loginName);
        verify(customerAccountRepository, never()).delete(any(CustomerAccount.class));
        verify(scanner, times(2)).next(); // check for the two inputs
    }
    // Add more tests for other scenarios, such as canceling the delete operation, handling non-existing account, etc.
}
