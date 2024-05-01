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
        verify(scanner).nextInt();
    }
    @Test
    void testContinueSearchingAfterNonExistentAccount() {

        Long firstAccountId = 2L;
        Long secondAccountId = 1L;
        CustomerAccount foundAccount = new CustomerAccount();
        foundAccount.setAccountid(secondAccountId);
        foundAccount.setUsername("john_doe");
        foundAccount.setPincode("12345");
        foundAccount.setName("John Doe");
        foundAccount.setBalance(1000.0);
        foundAccount.setStatus("active");

        when(scanner.nextLong()).thenReturn(firstAccountId, secondAccountId);
        when(scanner.nextInt()).thenReturn(9, 0);

        when(customerAccountRepository.findByAccountid(firstAccountId)).thenReturn(null);
        when(customerAccountRepository.findByAccountid(secondAccountId)).thenReturn(foundAccount);

        searchAccount.adminOperate();

        verify(customerAccountRepository).findByAccountid(firstAccountId);
        verify(customerAccountRepository).findByAccountid(secondAccountId);
        verify(scanner, times(2)).nextInt();
    }


}
