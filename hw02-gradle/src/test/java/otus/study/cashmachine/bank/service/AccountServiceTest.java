package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    AccountDao accountDao;

    AccountServiceImpl accountServiceImpl;

    @BeforeEach
    void setUp() {
        accountDao = mock(AccountDao.class);
        accountServiceImpl = new AccountServiceImpl(accountDao);
    }

    @Test
    void createAccountMock() {
        BigDecimal initialAmount = new BigDecimal("1000.00");
        Account account = new Account(1L, initialAmount);
        when(accountDao.saveAccount(any(Account.class))).thenReturn(account);

        Account createdAccount = accountServiceImpl.createAccount(initialAmount);

        assertNotNull(createdAccount);
        assertEquals(account.getId(), createdAccount.getId());
        assertEquals(account.getAmount(), createdAccount.getAmount());
    }

    @Test
    void createAccountCaptor() {
        BigDecimal initialAmount = new BigDecimal("1000.00");
        Account account = new Account(1L, initialAmount);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        when(accountDao.saveAccount(accountCaptor.capture())).thenReturn(account);

        Account createdAccount = accountServiceImpl.createAccount(initialAmount);

        assertNotNull(createdAccount);
        assertEquals(account.getId(), createdAccount.getId());
        assertEquals(account.getAmount(), createdAccount.getAmount());

        Account capturedAccount = accountCaptor.getValue();
        assertNotNull(capturedAccount);
        assertEquals(account.getId(), capturedAccount.getId());
        assertEquals(account.getAmount(), capturedAccount.getAmount());
    }

    @Test
    void addSum() {
        BigDecimal initialAmount = new BigDecimal("1000.00");
        BigDecimal depositAmount = new BigDecimal("500.00");
        BigDecimal expectedAmount = initialAmount.add(depositAmount);
        Account account = new Account(1L, initialAmount);

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal newAmount = accountServiceImpl.putMoney(1L, depositAmount);

        assertEquals(expectedAmount, newAmount);
        assertEquals(expectedAmount, account.getAmount());
    }

    @Test
    void getSum() {
        BigDecimal initialAmount = new BigDecimal("1000.00");
        BigDecimal withdrawalAmount = new BigDecimal("500.00");
        BigDecimal expectedAmount = initialAmount.subtract(withdrawalAmount);
        Account account = new Account(1L, initialAmount);

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal newAmount = accountServiceImpl.getMoney(1L, withdrawalAmount);

        assertEquals(expectedAmount, newAmount);
        assertEquals(expectedAmount, account.getAmount());
    }

    @Test
    void getAccount() {
        Account expectedAccount = new Account(1L, new BigDecimal("1000.00"));

        when(accountDao.getAccount(1L)).thenReturn(expectedAccount);

        Account account = accountServiceImpl.getAccount(1L);

        assertNotNull(account);
        assertEquals(expectedAccount.getId(), account.getId());
        assertEquals(expectedAccount.getAmount(), account.getAmount());
    }

    @Test
    void checkBalance() {
        BigDecimal initialAmount = new BigDecimal("1000.00");
        Account account = new Account(1L, initialAmount);

        when(accountDao.getAccount(1L)).thenReturn(account);

        BigDecimal balance = accountServiceImpl.checkBalance(1L);

        assertEquals(initialAmount, balance);
    }
}
