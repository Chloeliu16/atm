package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class CustomerLoginTest {

    @Mock
    private Scanner scanner;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private CustomerUI customerUI;

    private CustomerLogin customerLogin;

    @BeforeEach
    void setUp() {
        customerLogin = new CustomerLogin(scanner, customerAccountRepository, customerUI);
    }

    @Test
    void testSuccessfulLogin() {
        when(scanner.next()).thenReturn("john", "1234");
        CustomerAccount mockAccount = new CustomerAccount();
        when(customerAccountRepository.findByUsernameAndPincode("john", "1234")).thenReturn(mockAccount);

        customerLogin.accountLogin();

        verify(customerUI).showCustomerUI("john");
        verify(scanner, times(2)).next(); // Check that inputs are processed correctly
    }

    @Test
    void testFailedLoginAttempts() {
        when(scanner.next()).thenReturn("john", "wrongPin", "john", "1234");
        when(customerAccountRepository.findByUsernameAndPincode("john", "wrongPin")).thenReturn(null);
        CustomerAccount mockAccount = new CustomerAccount();
        when(customerAccountRepository.findByUsernameAndPincode("john", "1234")).thenReturn(mockAccount);

        customerLogin.accountLogin();

        verify(scanner, times(4)).next(); // Includes retry attempt
        verify(customerUI).showCustomerUI("john"); // Should eventually show UI after correct credentials
    }
    @Test
    void testExitLoginProcess() {
        when(scanner.next()).thenReturn("exit");

        customerLogin.accountLogin();

        verify(scanner).next();
        verifyNoMoreInteractions(customerAccountRepository);
        verifyNoInteractions(customerUI);
    }

}