package com.xiaojun.admin;

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
import java.util.InputMismatchException;
import java.util.Scanner;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AdminUITest {
    @Mock
    private IAdminOperate mockCreateAccount;
    @Mock
    private IAdminOperate mockDeleteAccount;
    @Mock
    private IAdminOperate mockUpdateAccount;
    @Mock
    private IAdminOperate mockSearchAccount;
    @Mock
    private Scanner mockScanner;

    private AdminUI adminUI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUI = new AdminUI(mockScanner, mockCreateAccount, mockDeleteAccount, mockUpdateAccount, mockSearchAccount);
    }

    @Test
    public void testCreateAccountOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(1, 5); // Simulate user selecting option 1 and then 5

        // Call the method under test
        adminUI.showAdminUI();

        // Verify that createAccount.adminOperate() is called
        verify(mockCreateAccount, times(1)).adminOperate();
    }

    @Test
    public void testDeleteAccountOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(2, 5); // Simulate user selecting option 2 and then 5

        // Call the method under test
        adminUI.showAdminUI();

        // Verify that deleteAccount.adminOperate() is called
        verify(mockDeleteAccount, times(1)).adminOperate();
    }

    @Test
    public void testUpdateAccountOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(3, 5); // Simulate user selecting option 3 and then 5

        // Call the method under test
        adminUI.showAdminUI();

        // Verify that updateAccount.adminOperate() is called
        verify(mockUpdateAccount, times(1)).adminOperate();
    }

    @Test
    public void testSearchAccountOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(4, 5); // Simulate user selecting option 4 and then 5

        // Call the method under test
        adminUI.showAdminUI();

        // Verify that searchAccount.adminOperate() is called
        verify(mockSearchAccount, times(1)).adminOperate();
    }

    @Test
    public void testExitOptionSelected() {
        // Mock user input
        when(mockScanner.nextInt()).thenReturn(5); // Simulate user selecting option 5

        // Call the method under test
        adminUI.showAdminUI();

        // Verify that the loop breaks and no other methods are called
        verify(mockCreateAccount, never()).adminOperate();
        verify(mockDeleteAccount, never()).adminOperate();
        verify(mockUpdateAccount, never()).adminOperate();
        verify(mockSearchAccount, never()).adminOperate();
    }

}
