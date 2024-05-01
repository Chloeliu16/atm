package com.xiaojun.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AdminAccountTest {

    @Test
    public void testConstructorAndGetters() {
        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        AdminAccount adminAccount = new AdminAccount(accountId, login, pincode);

        assertNotNull(adminAccount);
        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

    @Test
    public void testSetters() {
        AdminAccount adminAccount = new AdminAccount();

        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        adminAccount.setAccountId(accountId);
        adminAccount.setLogin(login);
        adminAccount.setPincode(pincode);

        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

    @Test
    public void testBuilder() {
        Long accountId = 1L;
        String login = "admin";
        String pincode = "1234";

        AdminAccount adminAccount = AdminAccount.builder()
                .accountId(accountId)
                .login(login)
                .pincode(pincode)
                .build();

        assertNotNull(adminAccount);
        assertEquals(accountId, adminAccount.getAccountId());
        assertEquals(login, adminAccount.getLogin());
        assertEquals(pincode, adminAccount.getPincode());
    }

}
