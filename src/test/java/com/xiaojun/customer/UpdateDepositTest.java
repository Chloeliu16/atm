package com.xiaojun.customer;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;
import static org.mockito.Mockito.*;

public class UpdateDepositTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockRepository;

    private UpdateDeposit updateDeposit;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        updateDeposit = new UpdateDeposit(mockScanner, mockRepository);
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
}
