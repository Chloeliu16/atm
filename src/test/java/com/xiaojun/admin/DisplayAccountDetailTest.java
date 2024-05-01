package com.xiaojun.admin;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.xiaojun.model.CustomerAccount;
import com.xiaojun.repository.CustomerAccountRepository;
import org.mockito.*;

public class DisplayAccountDetailTest {

    @InjectMocks
    private DisplayAccountDetail displayAccountDetail;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDisplayAccountExists() {

        Long accountId = 1L;
        CustomerAccount mockAccount = new CustomerAccount();
        mockAccount.setAccountid(accountId);
        mockAccount.setUsername("user1");
        mockAccount.setPincode("1234");
        mockAccount.setName("John Doe");
        mockAccount.setBalance(100.0);
        mockAccount.setStatus("Active");

        when(customerAccountRepository.findByAccountid(accountId)).thenReturn(mockAccount);

        displayAccountDetail.display(accountId);

        verify(customerAccountRepository).findByAccountid(accountId);

    }

    @Test
    public void testDisplayAccountException() {
        Long accountId = 3L;
        when(customerAccountRepository.findByAccountid(accountId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            displayAccountDetail.display(accountId);
        });

        verify(customerAccountRepository).findByAccountid(accountId);
    }
}
