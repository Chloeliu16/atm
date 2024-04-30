package com.xiaojun.admin;

import static org.mockito.Mockito.*;
import java.util.Scanner;
import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateAccountTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockCustomerAccountRepository;

    private CreateAccount createAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        createAccount = new CreateAccount(mockScanner, mockCustomerAccountRepository);
    }

    @Test
    public void testCreateAccount_Successful() {
        when(mockScanner.next())
                .thenReturn("newUser")  // 模拟登录名输入
                .thenReturn("12345")   // 第一次 PIN 码输入
                .thenReturn("active")  // 状态输入
                .thenReturn("exit");   // 退出输入

        when(mockScanner.nextLine())
                .thenReturn("")       // 清空行缓冲
                .thenReturn("John Doe"); // 模拟持有人姓名输入

        when(mockScanner.hasNextDouble()).thenReturn(true);
        when(mockScanner.nextDouble()).thenReturn(1000.0);

        when(mockCustomerAccountRepository.findByUsername("newUser")).thenReturn(null);

        createAccount.adminOperate();

        verify(mockCustomerAccountRepository).saveAndFlush(any(CustomerAccount.class));
        verify(mockScanner, times(2)).nextLine(); // 确保 nextLine() 被正确调用
        verify(mockScanner, times(4)).next(); // 包括退出前的所有 next 调用
    }

    @Test
    public void testCreateAccount_Failure_ExistingUsername() {
        when(mockScanner.next()).thenReturn("existingUser", "exit");
        when(mockCustomerAccountRepository.findByUsername("existingUser")).thenReturn(new CustomerAccount());

        createAccount.adminOperate();

        verify(mockCustomerAccountRepository, never()).saveAndFlush(any(CustomerAccount.class));
    }

    @Test
    public void testCreateAccount_InvalidPinCode() {
        when(mockScanner.next()).thenReturn("newUser", "1234", "12345", "exit");
        when(mockScanner.nextLine()).thenReturn("");  // Clear the buffer after invalid pin
        when(mockScanner.hasNextDouble()).thenReturn(true);
        when(mockScanner.nextDouble()).thenReturn(1000.0);
        when(mockCustomerAccountRepository.findByUsername("newUser")).thenReturn(null);

        createAccount.adminOperate();

        verify(mockCustomerAccountRepository).saveAndFlush(any(CustomerAccount.class));
    }

    @Test
    public void testExitCreationProcess() {
        when(mockScanner.next()).thenReturn("exit");

        createAccount.adminOperate();

        verify(mockCustomerAccountRepository, never()).saveAndFlush(any(CustomerAccount.class));
        verify(mockScanner, times(1)).next(); // Only one input for exit
    }
}
