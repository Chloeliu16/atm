package com.xiaojun.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CustomerAccountTest {

    @Test
    public void testCustomerAccountCreationWithBuilder() {
        CustomerAccount account = CustomerAccount.builder()
                .accountid(1L)
                .username("john_doe")
                .pincode("4321")
                .name("John Doe")
                .balance(1000.00)
                .status("Active")
                .build();

        assertNotNull(account);
        assertEquals(1L, account.getAccountid());
        assertEquals("john_doe", account.getUsername());
        assertEquals("4321", account.getPincode());
        assertEquals("John Doe", account.getName());
        assertEquals(1000.00, account.getBalance());
        assertEquals("Active", account.getStatus());
    }

    @Test
    public void testAccountBalanceOperations() {
        CustomerAccount account = new CustomerAccount();
        account.setBalance(500.00);

        assertEquals(500.00, account.getBalance());

        account.setBalance(account.getBalance() + 200.00); // Adding money
        assertEquals(700.00, account.getBalance());

        account.setBalance(account.getBalance() - 150.00); // Subtracting money
        assertEquals(550.00, account.getBalance());
    }

    @Test
    public void testAccountStatusChange() {
        CustomerAccount account = new CustomerAccount();
        account.setStatus("Pending");
        assertEquals("Pending", account.getStatus());

        account.setStatus("Closed");
        assertEquals("Closed", account.getStatus());
    }
}
