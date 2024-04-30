package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UpdateDepositTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockRepository;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private UpdateDeposit updateDeposit;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        updateDeposit = new UpdateDeposit(mockScanner, mockRepository);
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut); // Restore the original system output
    }


    @Test
    void testCustomerOperate_SuccessfulDeposit() {
        // Arrange
        String login = "user123";
        double depositAmount = 100.0;
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setBalance(200.0);
        mockAccount.setAccountid(1L);

        when(mockScanner.nextDouble()).thenReturn(depositAmount);
        when(mockScanner.next()).thenReturn("1"); // Simulating pressing any number to return
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount);
        when(mockRepository.saveAndFlush(any(CustomerAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        updateDeposit.customerOperate(login);

        // Assert
        verify(mockScanner, times(1)).nextDouble(); // Ensure amount is read from the user
        verify(mockRepository, times(1)).saveAndFlush(mockAccount); // Ensure the account is saved
        assert(mockAccount.getBalance() == 300.0);
    }

    @Test
    void testCustomerOperate_SuccessfulDeposit1() {
        // Arrange
        String login = "user123";
        double depositAmount = 100.0;
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setBalance(200.0);
        mockAccount.setAccountid(1L);

        when(mockScanner.nextDouble()).thenReturn(depositAmount);
        when(mockScanner.next()).thenReturn("1"); // Simulating pressing any number to return
        when(mockRepository.findByUsername(login)).thenReturn(mockAccount); // Ensure that findByUsername returns a non-null object
        when(mockRepository.saveAndFlush(any(CustomerAccount.class))).thenReturn(mockAccount); // Ensure that saveAndFlush returns a non-null object

        // Act
        updateDeposit.customerOperate(login);

        // Assert
        verify(mockScanner, times(1)).nextDouble(); // Ensure amount is read from the user
        verify(mockRepository, times(1)).saveAndFlush(mockAccount); // Ensure the account is saved
        assertEquals(300.0, mockAccount.getBalance(), 0.0); // Check that the balance is updated correctly
        assertTrue(outContent.toString().contains("Deposit Successfully! Your current balance is: 300.0"));
    }


}
