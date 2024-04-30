package com.xiaojun.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AdminAccountTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        // Act
        AdminAccount adminAccount = new AdminAccount(accountId, login, pincode);

        // Assert
        assertNotNull(adminAccount);
        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

    @Test
    public void testSetters() {
        // Arrange
        AdminAccount adminAccount = new AdminAccount();

        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        // Act
        adminAccount.setAccountId(accountId);
        adminAccount.setLogin(login);
        adminAccount.setPincode(pincode);

        // Assert
        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

    @Test
    public void testBuilder() {
        // Arrange
        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        // Act
        AdminAccount adminAccount = AdminAccount.builder()
                .accountId(accountId)
                .login(login)
                .pincode(pincode)
                .build();

        // Assert
        assertNotNull(adminAccount);
        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

}
