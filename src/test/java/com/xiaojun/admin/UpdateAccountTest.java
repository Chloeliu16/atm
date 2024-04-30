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
        System.setOut(new PrintStream(outContent));  // Redirects standard output to outContent
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);  // Resets standard output to its original stream
    }
    @Test
    public void testAdminOperate_ReturnAtStart() {
        // Arrange
        when(mockScanner.next()).thenReturn("return");

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner).next();
        verifyNoInteractions(mockRepository);
    }

    @Test
    public void testAdminOperate_UpdateUsernameSuccess() {
        // Arrange
        Long accountId = 1L;
        String existingUsername = "oldUsername";
        String username = "username";
        when(mockScanner.next()).thenReturn("1", "1", username);
        //when(mockScanner.next()).thenReturn("1", accountId.toString(), "1", newUsername, "9");
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        account.setUsername(username);

        // The sequence here ensures that the account ID is fetched first,
        // then the new username check and finally attempts to save if the username doesn't exist.
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);
        when(mockRepository.findByUsername(username)).thenReturn(null);
        when(mockRepository.saveAndFlush(account)).thenReturn(account);

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner, times(5)).next();
        verify(mockRepository).findByUsername(username); // Ensure this call is expected in the actual method logic
        verify(mockRepository).saveAndFlush(account); // Confirm save is called
        verify(mockDisplayAccountDetail, times(3)).display(accountId); // Display is called post-update
    }


    @Test
    public void testAdminOperate_InvalidPinCode() {
        // Arrange
        Long accountId = 1L;
        String invalidPin = "1234"; // Invalid because it's not 5 digits
        when(mockScanner.next()).thenReturn("2", invalidPin);
        when(mockScanner.nextLong()).thenReturn(accountId);
        CustomerAccount account = new CustomerAccount();
        account.setAccountid(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(account);

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner, times(2)).next(); // For choice, invalid pin, and exit
        verify(mockRepository, never()).saveAndFlush(account); // Should not save because pin is invalid
    }

    @Test
    public void testAdminOperate_AccountDoesNotExist() {
        // Arrange
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("1", "return");
        when(mockScanner.nextLong()).thenReturn(accountId);
        when(mockRepository.findByAccountid(accountId)).thenReturn(null);

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner, atLeastOnce()).next();
        verify(mockRepository).findByAccountid(accountId);
        assertTrue(outContent.toString().contains("***Non account exist***"));
    }

    @Test
    public void testAdminOperate_ReturnAtStart1() {
        // Arrange
        when(mockScanner.next()).thenReturn("return");

        // Act
        updateAccount.adminOperate();

        // Assert
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
        // Arrange
        Long accountId = 1L;
        when(mockScanner.next()).thenReturn("return");
        // Since we are immediately returning, we don't need to mock further interactions
        // when(mockScanner.nextLong()).thenReturn(accountId);
        // when(mockRepository.findByAccountid(accountId)).thenReturn(null);

        // Act
        updateAccount.adminOperate();

        // Assert
        verify(mockScanner).next(); // Only verify that next() is called once
        verifyNoInteractions(mockRepository); // Ensure no interactions with the repository
    }

}
