package com.example.bankofsamaratest.service;

import com.example.bankofsamaratest.model.BankAccount;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.BankAccountRepository;
import com.example.bankofsamaratest.repository.UserRepository;
import com.example.bankofsamaratest.service.Impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateBalance_ShouldUpdateBalances() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        BankAccount account1 = new BankAccount();
        account1.setBalance(BigDecimal.valueOf(100));
        account1.setInitialDeposit(BigDecimal.valueOf(100));
        user1.setAccount(account1);
        userList.add(user1);

        User user2 = new User();
        user2.setId(2L);
        BankAccount account2 = new BankAccount();
        account2.setBalance(BigDecimal.valueOf(200));
        account2.setInitialDeposit(BigDecimal.valueOf(200));
        user2.setAccount(account2);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        bankAccountService.updateBalance();

        // Assert
        assertEquals(BigDecimal.valueOf(105), account1.getBalance());
        assertEquals(BigDecimal.valueOf(207), account2.getBalance());
        verify(bankAccountRepository, times(1)).saveAll(anyList());
    }

    @Test
    void transferMoney_ShouldTransferMoneySuccessfully() {

        User userFrom = new User();
        userFrom.setId(1L);
        BankAccount fromAccount = new BankAccount();
        fromAccount.setBalance(BigDecimal.valueOf(100));
        userFrom.setAccount(fromAccount);

        User userTo = new User();
        userTo.setId(2L);
        BankAccount toAccount = new BankAccount();
        toAccount.setBalance(BigDecimal.valueOf(200));
        userTo.setAccount(toAccount);

        BigDecimal amountToTransfer = BigDecimal.valueOf(50);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userFrom));
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(userTo));

        // Act
        boolean result = bankAccountService.transferMoney(1L, 2L, amountToTransfer);

        // Assert
        assertTrue(result);
        assertEquals(BigDecimal.valueOf(50), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(250), toAccount.getBalance());
        verify(bankAccountRepository, times(2)).save(any());
    }
}
