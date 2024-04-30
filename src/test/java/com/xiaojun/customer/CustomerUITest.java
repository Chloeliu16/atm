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
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(1, 5); // Simulate user selecting option 1 and then 5
        String login = "testLogin";

        // Call the method under test
        customerUI.showCustomerUI(login);

        // Verify that withdraw.customerOperate(login) is called
        verify(mockWithdraw, times(1)).customerOperate(login);
    }

    @Test
    public void testDepositOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(3, 5); // Simulate user selecting option 3 and then 5
        String login = "testLogin";

        // Call the method under test
        customerUI.showCustomerUI(login);

        // Verify that updateDeposit.customerOperate(login) is called
        verify(mockUpdateDeposit, times(1)).customerOperate(login);
    }

    @Test
    public void testDisplayBalanceOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(4, 5); // Simulate user selecting option 4 and then 5
        String login = "testLogin";

        // Call the method under test
        customerUI.showCustomerUI(login);

        // Verify that displayBalance.customerOperate(login) is called
        verify(mockDisplayBalance, times(1)).customerOperate(login);
    }

    @Test
    public void testExitOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(5); // Simulate user selecting option 5
        String login = "testLogin";

        // Call the method under test
        customerUI.showCustomerUI(login);

        // Verify that the loop breaks and no other methods are called
        verify(mockWithdraw, never()).customerOperate(anyString());
        verify(mockUpdateDeposit, never()).customerOperate(anyString());
        verify(mockDisplayBalance, never()).customerOperate(anyString());
    }

    @Test
    public void testInvalidInputHandled() {
        // Mock user input
        when(mockScanner.nextInt()).thenThrow(new InputMismatchException()).thenReturn(5); // Simulate invalid input and then 5
        String login = "testLogin";

        // Call the method under test
        customerUI.showCustomerUI(login);

        // Verify that the error message is printed
        // Verify that the loop continues after an invalid input
        verify(mockScanner, times(2)).nextInt();
    }
    @Test
    public void testUnrecognizedCommandHandled() {
        when(mockScanner.nextInt()).thenReturn(999, 5); // An unrecognized command, then exit
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockScanner, times(2)).nextInt(); // Verify that nextInt is called twice
        verifyNoInteractions(mockWithdraw); // Ensure no operations are accidentally triggered
        verifyNoInteractions(mockUpdateDeposit);
        verifyNoInteractions(mockDisplayBalance);
    }
    @Test
    public void testMultipleInvalidInputs() {
        when(mockScanner.nextInt()).thenThrow(new InputMismatchException())
                .thenThrow(new InputMismatchException())
                .thenReturn(4, 5); // Two invalid inputs, followed by a valid input to display balance, then exit
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockDisplayBalance, times(1)).customerOperate(login); // Verify balance display is eventually called
        verify(mockScanner, times(4)).nextInt(); // Check scanner is called the correct number of times
    }
    @Test
    public void testContinuousValidCommands() {
        when(mockScanner.nextInt()).thenReturn(1, 3, 4, 5); // Withdraw, Deposit, Display Balance, then Exit
        String login = "testLogin";

        customerUI.showCustomerUI(login);

        verify(mockWithdraw, times(1)).customerOperate(login); // Each operation is triggered once
        verify(mockUpdateDeposit, times(1)).customerOperate(login);
        verify(mockDisplayBalance, times(1)).customerOperate(login);
        verify(mockScanner, times(4)).nextInt(); // Scanner should be called once for each operation
    }

}
