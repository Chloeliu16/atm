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
        String login = "user123";
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(1500.0);
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount);
        when(mockScanner.next()).thenReturn("1");

        displayBalance.customerOperate(login);

        verify(mockRepository, times(1)).findByUsername(login);
        verify(mockScanner, times(1)).next();
    }

    @Test
    public void testCustomerOperate_DataAccessException() {
        String login = "user123";
        when(mockRepository.findByUsername(login)).thenThrow(new DataAccessException("Database error") {
        });

        displayBalance.customerOperate(login);

        verify(mockRepository, times(1)).findByUsername(login);

        assertTrue(outContent.toString().contains("Error accessing data: Database error"));
        verify(mockScanner, never()).next();
    }

    @Test
    public void testCustomerOperate_AccountNotFound() {

        String login = "user123";
        when(mockRepository.findByUsername(login)).thenReturn(null);

        displayBalance.customerOperate(login);


        verify(mockRepository, times(1)).findByUsername(login);
        assertTrue(outContent.toString().contains("No account found for the provided login details."));
        verify(mockScanner, never()).next();
    }

    @Test
    public void testCustomerOperate_MultipleInputsBeforeExit() {

        String login = "user123";
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(1500.0);
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount);
        when(mockScanner.next()).thenReturn("1");

        displayBalance.customerOperate(login);

        verify(mockRepository, times(1)).findByUsername(login);
        verify(mockScanner, times(1)).next();
    }
}
