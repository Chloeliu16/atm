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
        doNothing().when(ilogin).accountLogin();

        atm.login(ilogin);

        verify(ilogin).accountLogin();
    }

    @Test
    public void testLoginFailure() {
        doThrow(new RuntimeException("Login failed")).when(ilogin).accountLogin();

        try {
            atm.login(ilogin);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Login failed", e.getMessage());
        }

        verify(ilogin).accountLogin();
    }

    @Test(expected = Exception.class)
    public void testLoginThrowsException() {

        doThrow(new Exception("System error")).when(ilogin).accountLogin();

        atm.login(ilogin);

    }
}
