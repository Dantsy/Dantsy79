package otus.study.cashmachine.bank.service;

import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountDao accountDao;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        accountService = new AccountServiceImpl(accountDao);
    }

    @Test
    void CreateAccount() {
        Account account = new Account(0, BigDecimal.valueOf(1000));

        when(accountDao.saveAccount(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(BigDecimal.valueOf(1000));

        verify(accountDao).saveAccount(any(Account.class));

        assertEquals(account, createdAccount);
    }

    @Test
    void GetAccount() {
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        when(accountDao.getAccount(1L)).thenReturn(account);

        Account retrievedAccount = accountService.getAccount(1L);

        verify(accountDao).getAccount(1L);

        assertEquals(account, retrievedAccount);
    }

    @Test
    void PutMoney() {
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal newAmount = accountService.putMoney(1L, BigDecimal.valueOf(500));

        verify(accountDao).getAccount(1L);

        assertEquals(BigDecimal.valueOf(1500), newAmount);
    }

    @Test
    void GetMoney() {
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal newAmount = accountService.getMoney(1L, BigDecimal.valueOf(500));

        verify(accountDao).getAccount(1L);

        assertEquals(BigDecimal.valueOf(500), newAmount);
    }

    @Test
    void CheckBalance() {
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal balance = accountService.checkBalance(1L);

        verify(accountDao).getAccount(1L);

        assertEquals(BigDecimal.valueOf(1000), balance);
    }

    @Test
    void GetMoneyInsufficientFunds() {
        Account account = new Account(1L, BigDecimal.valueOf(100));

        when(accountDao.getAccount(1L)).thenReturn(account);

        assertThrows(IllegalArgumentException.class, () -> accountService.getMoney(1L, BigDecimal.valueOf(200)));

        verify(accountDao).getAccount(1L);
    }
}