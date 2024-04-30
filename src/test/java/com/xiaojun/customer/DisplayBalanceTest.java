package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.Scanner;

public class DisplayBalanceTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockRepository;

    private DisplayBalance displayBalance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        displayBalance = new DisplayBalance(mockScanner, mockRepository);
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
}
