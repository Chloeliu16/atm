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
        when(mockScanner.nextInt()).thenReturn(1, 5);

        adminUI.showAdminUI();

        verify(mockCreateAccount, times(1)).adminOperate();
    }

    @Test
    public void testDeleteAccountOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(2, 5);

        adminUI.showAdminUI();

        verify(mockDeleteAccount, times(1)).adminOperate();
    }

    @Test
    public void testUpdateAccountOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(3, 5);

        adminUI.showAdminUI();

        verify(mockUpdateAccount, times(1)).adminOperate();
    }

    @Test
    public void testSearchAccountOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(4, 5);

        adminUI.showAdminUI();

        verify(mockSearchAccount, times(1)).adminOperate();
    }

    @Test
    public void testExitOptionSelected() {
        when(mockScanner.nextInt()).thenReturn(5);

        adminUI.showAdminUI();

        verify(mockCreateAccount, never()).adminOperate();
        verify(mockDeleteAccount, never()).adminOperate();
        verify(mockUpdateAccount, never()).adminOperate();
        verify(mockSearchAccount, never()).adminOperate();
    }

}
