package com.xiaojun.admin;

import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Scanner;

public class UpdateAccountTest {

    @Mock
    private Scanner mockScanner;
    @Mock
    private CustomerAccountRepository mockRepository;
    @Mock
    private DisplayAccountDetail mockDisplayAccountDetail;

    private UpdateAccount updateAccount;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        updateAccount = new UpdateAccount(mockScanner, mockRepository, mockDisplayAccountDetail);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    public void testAdminOperate_ReturnAtStart() {

        when(mockScanner.next()).thenReturn("return");

        updateAccount.adminOperate();

        verify(mockScanner).next();
        verifyNoInteractions(mockRepository);
    }

    @Test
    public void testAdminOperate_UpdateUsernameSuccess() {

        Long accountId = 1L;
        String existingUsername = "oldUsername";
        String username = "username";
        when(mockScanner.next()).thenReturn("1", "1", username);

        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        account.setUsername(username);

        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.findByUsername(username)).thenReturn(null);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(5)).next();
        verify(mockRepository).findByUsername(username);
        verify(mockRepository).saveAndFlush(account);
        verify(mockDisplayAccountDetail, times(3)).display(accountId);
    }


    @Test
    public void testAdminOperate_InvalidPinCode() {

        Long accountId = 1L;
        String invalidPin = "1234";
        when(mockScanner.next()).thenReturn("2", invalidPin);
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(2)).next();
        verify(mockRepository, never()).saveAndFlush(account);
    }

    @Test
    public void testAdminOperate_AccountDoesNotExist() {
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("1", "return");
        when(mockScanner.nextLong()).thenReturn(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(null);

        updateAccount.adminOperate();

        verify(mockScanner, atLeastOnce()).next();
        verify(mockRepository).findByAccountid(accountId);
        assertTrue(outContent.toString().contains("***Non account exist***"));
    }

    @Test
    public void testAdminOperate_ReturnAtStart1() {

        when(mockScanner.next()).thenReturn("return");

        updateAccount.adminOperate();

        verify(mockScanner).next();
        verifyNoInteractions(mockRepository);
    }

    @Test
    public void testAdminOperate_InvalidOption() {
        // Arrange
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("invalid", "return");
        when(mockScanner.nextLong()).thenReturn(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(new CustomerAccount());

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner, atLeastOnce()).next();
        verify(mockRepository).findByAccountid(accountId);
        assertTrue(outContent.toString().contains("***Have exited***"));
    }

    @Test
    public void testAdminOperate_NonExistentAccount() {
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("return");

        updateAccount.adminOperate();

        verify(mockScanner).next();
        verifyNoInteractions(mockRepository);
    }
    @Test
    public void testAdminOperate_UpdatePinCodeSuccess() {
        Long accountId = 1L;
        String validPin = "12345";
        when(mockScanner.next()).thenReturn("1","2", validPin);
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(5)).next();
        verify(mockRepository).saveAndFlush(account);
        assertTrue(outContent.toString().contains("Update pin code Successfully!"));
    }

    @Test
    public void testAdminOperate_UpdateHolderNameSuccess() {
        Long accountId = 1L;
        String newHolderName = "John Doe";
        when(mockScanner.next()).thenReturn("1", "3", newHolderName);
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(5)).next();
        verify(mockRepository).saveAndFlush(account);
        assertTrue(outContent.toString().contains("Update holder name Successfully!"));
    }
    @Test
    public void testAdminOperate_UpdateStatusSuccess() {

        Long accountId = 1L;
        String newBalance = "1000";
        when(mockScanner.next()).thenReturn("1", "4", newBalance);
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(5)).next();
        verify(mockRepository).saveAndFlush(account);
        assertTrue(outContent.toString().contains("Update status Successfully!"));
    }
    @Test
    public void testAdminOperate_ExitOnInputNine() {
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("1", "9");
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(2)).next();
        verify(mockRepository, never()).saveAndFlush(any(CustomerAccount.class));
        assertTrue(outContent.toString().contains("***Have exited***"));
    }

    @Test
    public void testAdminOperate_MultipleInvalidPinBeforeSuccess() {

        Long accountId = 1L;
        String invalidPin = "1234";
        String validPin = "54321";
        when(mockScanner.next()).thenReturn("1", "2", invalidPin, invalidPin, validPin, "9");
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        updateAccount.adminOperate();

        verify(mockScanner, times(7)).next();
        verify(mockRepository).saveAndFlush(account);
        assertTrue(outContent.toString().contains("Update pin code Successfully!"));
    }

}
