package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class DisplayBalanceTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockRepository;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @InjectMocks
    private DisplayBalance displayBalance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        displayBalance = new DisplayBalance(mockScanner, mockRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut); // Restore the original system output
    }

    @Test
    public void testCustomerOperate_DisplaySuccessful() {
        // Arrange
        String login = "user123";
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(1500.0);
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount);
        when(mockScanner.next()).thenReturn("1");

        // Act
        displayBalance.customerOperate(login);

        // Assert
        verify(mockRepository, times(1)).findByUsername(login);
        verify(mockScanner, times(1)).next();
    }

    @Test
    public void testCustomerOperate_DataAccessException() {
        // Arrange
        String login = "user123";
        when(mockRepository.findByUsername(login)).thenThrow(new DataAccessException("Database error") {
        });

        // Act
        displayBalance.customerOperate(login);

        // Assert
        verify(mockRepository, times(1)).findByUsername(login);
        // You might need to capture output to the console if you want to assert on the error message printed
        // This assumes you have setup to capture System.out output to a ByteArrayOutputStream
        assertTrue(outContent.toString().contains("Error accessing data: Database error"));
        verify(mockScanner, never()).next(); // Ensure no further interaction after the exception
    }

    @Test
    public void testCustomerOperate_AccountNotFound() {
        // Arrange
        String login = "user123";
        when(mockRepository.findByUsername(login)).thenReturn(null);

        // Act
        displayBalance.customerOperate(login);

        // Assert
        verify(mockRepository, times(1)).findByUsername(login);
        assertTrue(outContent.toString().contains("No account found for the provided login details."));
        verify(mockScanner, never()).next(); // Ensuring that the method exits before asking for any input
    }

    @Test
    public void testCustomerOperate_MultipleInputsBeforeExit() {
        // Arrange
        String login = "user123";
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(1500.0);
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount);
        when(mockScanner.next()).thenReturn("1"); // Correctly simulate inputs

        // Act
        displayBalance.customerOperate(login);

        // Assert
        verify(mockRepository, times(1)).findByUsername(login);
        verify(mockScanner, times(1)).next(); // Ensure nextInt() is called for command inputs
    }


}
