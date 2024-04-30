package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class SearchAccountTest {

    @Mock
    private Scanner scanner;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @InjectMocks
    private SearchAccount searchAccount;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSearchAndFindAccountThenExit() {
        Long accountId = 1L;
        CustomerAccount foundAccount = new CustomerAccount(); // Assume it's properly instantiated with details.
        foundAccount.setAccountid(accountId);
        foundAccount.setUsername("john_doe");
        foundAccount.setPincode("12345");
        foundAccount.setName("John Doe");
        foundAccount.setBalance(1000.0);
        foundAccount.setStatus("active");

        when(scanner.nextLong()).thenReturn(accountId);
        when(customerAccountRepository.findByAccountid(accountId)).thenReturn(foundAccount);
        when(scanner.nextInt()).thenReturn(0);

        searchAccount.adminOperate();

        verify(customerAccountRepository).findByAccountid(accountId);
        verify(scanner).nextInt(); // To capture the exit command
    }
    @Test
    void testSearchNonExistentAccountThenExit() {
        Long accountId = 2L;

        when(scanner.nextLong()).thenReturn(accountId);
        when(customerAccountRepository.findByAccountid(accountId)).thenReturn(null);
        when(scanner.nextInt()).thenReturn(0);

        searchAccount.adminOperate();

        verify(customerAccountRepository).findByAccountid(accountId);
        verify(scanner).nextInt(); // To capture the exit command
    }
    @Test
    void testContinueSearchingAfterNonExistentAccount() {
        // Setup
        Long firstAccountId = 2L; // First account does not exist
        Long secondAccountId = 1L; // Second account exists
        CustomerAccount foundAccount = new CustomerAccount();
        foundAccount.setAccountid(secondAccountId);
        foundAccount.setUsername("john_doe");
        foundAccount.setPincode("12345");
        foundAccount.setName("John Doe");
        foundAccount.setBalance(1000.0);
        foundAccount.setStatus("active");

        // Arrange mock scanner inputs
        when(scanner.nextLong()).thenReturn(firstAccountId, secondAccountId);
        when(scanner.nextInt()).thenReturn(9, 0); // Continue after first, exit after second

        // Arrange repository responses
        when(customerAccountRepository.findByAccountid(firstAccountId)).thenReturn(null);
        when(customerAccountRepository.findByAccountid(secondAccountId)).thenReturn(foundAccount);

        // Act
        searchAccount.adminOperate();

        // Assert
        verify(customerAccountRepository).findByAccountid(firstAccountId); // Verify that the first ID was searched
        verify(customerAccountRepository).findByAccountid(secondAccountId); // Verify that the second ID was searched
        verify(scanner, times(2)).nextInt(); // Verify that nextInt was called twice
    }


}
