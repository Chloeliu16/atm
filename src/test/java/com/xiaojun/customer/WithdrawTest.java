package com.xiaojun.customer;

import static org.mockito.Mockito.*;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class WithdrawTest {

    @Mock
    private Scanner scanner;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @InjectMocks
    private Withdraw withdraw;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));  // Redirect System.out to capture console output
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut); // Restore the original System.out
    }

    @Test
    void testSuccessfulWithdrawal() {
        String login = "user123";
        double balance = 1000.0;
        double withdrawalAmount = 200.0;

        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(balance);

        when(customerAccountRepository.findByUsername(login)).thenReturn(mockAccount);
        when(scanner.nextDouble()).thenReturn(withdrawalAmount);
        when(scanner.next()).thenReturn("1");

        withdraw.customerOperate(login);

        verify(customerAccountRepository).saveAndFlush(mockAccount);
        assertTrue(outContent.toString().contains("Cash Successfully Withdrawn!"));
        assertEquals(800.0, mockAccount.getBalance(), "The balance should be updated correctly.");
    }

    @Test
    void testInsufficientFunds() {
        String login = "user321";
        double balance = 300.0;
        double withdrawalAmountFail = 400.0;
        double withdrawalAmountSuccess = 200.0;

        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(1L);
        mockAccount.setBalance(balance);

        when(customerAccountRepository.findByUsername(login)).thenReturn(mockAccount);
        when(scanner.nextDouble()).thenReturn(withdrawalAmountFail);
        when(scanner.nextDouble()).thenReturn(withdrawalAmountSuccess);
        when(scanner.next()).thenReturn("1");

        withdraw.customerOperate(login);

        verify(customerAccountRepository).saveAndFlush(mockAccount);
        assertTrue(outContent.toString().contains("Cash Successfully Withdrawn!"));
        assertEquals(100.0, mockAccount.getBalance(), "The balance should be updated correctly.");
    }
}
