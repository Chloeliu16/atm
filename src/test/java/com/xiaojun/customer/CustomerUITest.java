package com.xiaojun.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Scanner;
import java.util.InputMismatchException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class CustomerUITest {
    @Mock
    private ICustomerOperate mockDisplayBalance;
    @Mock
    private ICustomerOperate mockUpdateDeposit;
    @Mock
    private ICustomerOperate mockWithdraw;
    @Mock
    private Scanner mockScanner;

    private CustomerUI customerUI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerUI = new CustomerUI(mockScanner, mockDisplayBalance, mockUpdateDeposit, mockWithdraw);
    }

    @Test
    public void testWithdrawOptionSelected() {

        when(mockScanner.nextInt()).thenReturn(1, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockWithdraw, times(1)).customerOperate(login);
    }

    @Test
    public void testDepositOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(3, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockUpdateDeposit, times(1)).customerOperate(login);
    }

    @Test
    public void testDisplayBalanceOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(4, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockDisplayBalance, times(1)).customerOperate(login);
    }

    @Test
    public void testExitOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockWithdraw, never()).customerOperate(anyString());
        verify(mockUpdateDeposit, never()).customerOperate(anyString());
        verify(mockDisplayBalance, never()).customerOperate(anyString());
    }

    @Test
    public void testInvalidInputHandled() {
        when(mockScanner.nextInt()).thenThrow(new InputMismatchException()).thenReturn(5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockScanner, times(2)).nextInt();
    }
    @Test
    public void testUnrecognizedCommandHandled() {
        when(mockScanner.nextInt()).thenReturn(999, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockScanner, times(2)).nextInt();
        verifyNoInteractions(mockWithdraw);
        verifyNoInteractions(mockUpdateDeposit);
        verifyNoInteractions(mockDisplayBalance);
    }
    @Test
    public void testMultipleInvalidInputs() {
        when(mockScanner.nextInt()).thenThrow(new InputMismatchException())
                .thenThrow(new InputMismatchException())
                .thenReturn(4, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockDisplayBalance, times(1)).customerOperate(login);
        verify(mockScanner, times(4)).nextInt();
    }
    @Test
    public void testContinuousValidCommands() {
        when(mockScanner.nextInt()).thenReturn(1, 3, 4, 5);
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockWithdraw, times(1)).customerOperate(login);
        verify(mockUpdateDeposit, times(1)).customerOperate(login);
        verify(mockDisplayBalance, times(1)).customerOperate(login);
        verify(mockScanner, times(4)).nextInt();
    }

}
