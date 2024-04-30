package com.xiaojun.login;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class ATMTest {

    @Mock
    private ILogin ilogin;

    private ATM atm;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        atm = new ATM();
    }

    @Test
    public void testLoginSuccess() {
        // 模拟登录成功的行为
        doNothing().when(ilogin).accountLogin();  // 当accountLogin被调用时不做任何事情（模拟登录成功）

        // 调用ATM的登录方法
        atm.login(ilogin);

        // 验证是否调用了ILogin的accountLogin方法
        verify(ilogin).accountLogin();
    }

    @Test
    public void testLoginFailure() {
        // 模拟登录失败的行为，可以通过抛出异常来表示
        doThrow(new RuntimeException("Login failed")).when(ilogin).accountLogin();

        try {
            atm.login(ilogin);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Login failed", e.getMessage());
        }

        // 验证是否调用了ILogin的accountLogin方法
        verify(ilogin).accountLogin();
    }

    @Test(expected = Exception.class)
    public void testLoginThrowsException() {
        // 模拟登录过程中发生异常
        doThrow(new Exception("System error")).when(ilogin).accountLogin();

        // 调用ATM的登录方法，期望抛出异常
        atm.login(ilogin);

        // 此测试方法预期抛出异常，因此不需要额外的assertions
    }
}
