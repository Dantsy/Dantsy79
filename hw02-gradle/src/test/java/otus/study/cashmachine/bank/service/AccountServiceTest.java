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
        // Создаем аккаунт
        Account account = new Account(0, BigDecimal.valueOf(1000));

        // Настраиваем мок для метода saveAccount
        when(accountDao.saveAccount(any(Account.class))).thenReturn(account);

        // Вызываем метод createAccount
        Account createdAccount = accountService.createAccount(BigDecimal.valueOf(1000));

        // Проверяем, что метод saveAccount был вызван с правильным аргументом
        verify(accountDao).saveAccount(any(Account.class));

        // Проверяем, что созданный аккаунт соответствует ожидаемому
        assertEquals(account, createdAccount);
    }

    @Test
    void GetAccount() {
        // Создаем аккаунт
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        // Настраиваем мок для метода getAccount
        when(accountDao.getAccount(1L)).thenReturn(account);

        // Вызываем метод getAccount
        Account retrievedAccount = accountService.getAccount(1L);

        // Проверяем, что метод getAccount был вызван с правильным аргументом
        verify(accountDao).getAccount(1L);

        // Проверяем, что вернулся правильный аккаунт
        assertEquals(account, retrievedAccount);
    }

    @Test
    void PutMoney() {
        // Создаем аккаунт
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        // Настраиваем мок для метода getAccount
        when(accountDao.getAccount(1L)).thenReturn(account);

        // Вызываем метод putMoney
        BigDecimal newAmount = accountService.putMoney(1L, BigDecimal.valueOf(500));

        // Проверяем, что метод getAccount был вызван с правильным аргументом
        verify(accountDao).getAccount(1L);

        // Проверяем, что сумма на счете изменилась
        assertEquals(BigDecimal.valueOf(1500), newAmount);
    }

    @Test
    void GetMoney() {
        // Создаем аккаунт
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        // Настраиваем мок для метода getAccount
        when(accountDao.getAccount(1L)).thenReturn(account);

        // Вызываем метод getMoney
        BigDecimal newAmount = accountService.getMoney(1L, BigDecimal.valueOf(500));

        // Проверяем, что метод getAccount был вызван с правильным аргументом
        verify(accountDao).getAccount(1L);

        // Проверяем, что сумма на счете изменилась
        assertEquals(BigDecimal.valueOf(500), newAmount);
    }

    @Test
    void CheckBalance() {
        // Создаем аккаунт
        Account account = new Account(1L, BigDecimal.valueOf(1000));

        // Настраиваем мок для метода getAccount
        when(accountDao.getAccount(1L)).thenReturn(account);

        // Вызываем метод checkBalance
        BigDecimal balance = accountService.checkBalance(1L);

        // Проверяем, что метод getAccount был вызван с правильным аргументом
        verify(accountDao).getAccount(1L);

        // Проверяем, что вернулся правильный баланс
        assertEquals(BigDecimal.valueOf(1000), balance);
    }

    @Test
    void GetMoneyInsufficientFunds() {
        // Создаем аккаунт с недостаточным балансом
        Account account = new Account(1L, BigDecimal.valueOf(100));

        // Настраиваем мок для метода getAccount
        when(accountDao.getAccount(1L)).thenReturn(account);

        // Проверяем, что метод getMoney выбрасывает исключение при недостатке средств
        assertThrows(IllegalArgumentException.class, () -> accountService.getMoney(1L, BigDecimal.valueOf(200)));

        // Проверяем, что метод getAccount был вызван с правильным аргументом
        verify(accountDao).getAccount(1L);
    }
}