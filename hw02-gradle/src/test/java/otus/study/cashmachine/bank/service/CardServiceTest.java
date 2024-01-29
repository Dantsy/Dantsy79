package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.bank.service.AccountService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {
    AccountService accountService;

    CardsDao cardsDao;

    CardService cardService;
    CardServiceImpl cardServiceImpl;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        cardsDao = mock(CardsDao.class);
        cardServiceImpl = new CardServiceImpl(accountService, cardsDao);
    }

    @Test
    void createCard() {
        String number = "1234567890";
        Long accountId = 1L;
        String pinCode = "1234";
        Card expectedCard = new Card(1L, number, accountId, cardServiceImpl.getHash(pinCode));

        when(cardsDao.createCard(number, accountId, pinCode)).thenReturn(expectedCard);

        Card createdCard = cardServiceImpl.createCard(number, accountId, pinCode);

        assertNotNull(createdCard);
        assertEquals(expectedCard.getId(), createdCard.getId());
        assertEquals(expectedCard.getNumber(), createdCard.getNumber());
        assertEquals(expectedCard.getAccountId(), createdCard.getAccountId());
        assertEquals(expectedCard.getPinCode(), createdCard.getPinCode());
    }

    @Test
    void checkBalance() {
        Card card = new Card(1L, "1234", 1L, TestUtil.getHash("0000"));
        when(cardsDao.getCardByNumber(anyString())).thenReturn(card);
        when(accountService.checkBalance(1L)).thenReturn(new BigDecimal(1000));

        BigDecimal sum = cardService.getBalance("1234", "0000");
        assertEquals(0, sum.compareTo(new BigDecimal(1000)));
    }

    @Test
    void getMoney() {
        String number = "1234567890";
        String pin = "1234";
        BigDecimal sum = new BigDecimal("100.00");
        Long accountId = 1L;
        Card card = new Card(1L, number, accountId, cardServiceImpl.getHash(pin));

        when(cardsDao.getCardByNumber(number)).thenReturn(card);
        when(accountService.getMoney(accountId, sum)).thenReturn(sum);

        BigDecimal newBalance = cardServiceImpl.getMoney(number, pin, sum);

        assertEquals(sum, newBalance);
    }

    @Test
    void putMoney() {
        String number = "1234567890";
        String pin = "1234";
        BigDecimal sum = new BigDecimal("100.00");
        Long accountId = 1L;
        Card card = new Card(1L, number, accountId, cardServiceImpl.getHash(pin));

        when(cardsDao.getCardByNumber(number)).thenReturn(card);
        when(accountService.putMoney(accountId, sum)).thenReturn(sum);

        BigDecimal newBalance = cardServiceImpl.putMoney(number, pin, sum);

        assertEquals(sum, newBalance);
    }

    @Test
    void checkIncorrectPin() {
        Card card = new Card(1L, "1234", 1L, "0000");
        when(cardsDao.getCardByNumber(eq("1234"))).thenReturn(card);

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.getBalance("1234", "0012");
        });
        assertEquals(thrown.getMessage(), "Pincode is incorrect");
    }

    @Test
    void changePin() {
        String number = "1234567890";
        String oldPin = "1234";
        String newPin = "5678";
        Card card = new Card(1L, number, 1L, cardServiceImpl.getHash(oldPin));

        when(cardsDao.getCardByNumber(number)).thenReturn(card);
        when(cardsDao.saveCard(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = cardServiceImpl.cnangePin(number, oldPin, newPin);

        assertTrue(result);
        assertEquals(cardServiceImpl.getHash(newPin), card.getPinCode());
    }

    @Test
    void getBalance() {
        String number = "1234567890";
        String pin = "1234";
        BigDecimal balance = new BigDecimal("100.00");
        Long accountId = 1L;
        Card card = new Card(1L, number, accountId, cardServiceImpl.getHash(pin));

        when(cardsDao.getCardByNumber(number)).thenReturn(card);
        when(accountService.checkBalance(accountId)).thenReturn(balance);

        BigDecimal retrievedBalance = cardServiceImpl.getBalance(number, pin);

        assertEquals(balance, retrievedBalance);
    }
}