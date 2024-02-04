package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CardServiceTest {
    private AccountService accountService;
    private CardsDao cardsDao;
    private CardService cardService;

    @BeforeEach
    void init() {
        cardsDao = mock(CardsDao.class);
        accountService = mock(AccountService.class);
        cardService = new CardServiceImpl(accountService, cardsDao);
    }

    @Test
    void testCreateCard() {
        when(cardsDao.createCard("5555", 1L, "0123")).thenReturn(
                new Card(1L, "5555", 1L, "0123"));

        Card newCard = cardService.createCard("5555", 1L, "0123");
        assertNotEquals(0, newCard.getId());
        assertEquals("5555", newCard.getNumber());
        assertEquals(1L, newCard.getAccountId());
        assertEquals("0123", newCard.getPinCode());
    }

    @Test
    void checkBalance() {
        String number = "1234";
        Long accountId = 1L;
        String pinCode = "0000";
        String hashedPinCode = TestUtil.getHash(pinCode);
        Card card = new Card(1L, number, accountId, hashedPinCode);
        when(cardsDao.getCardByNumber(number)).thenReturn(card);
        when(accountService.checkBalance(accountId)).thenReturn(new BigDecimal(1000));

        BigDecimal balance = cardService.getBalance(number, pinCode);
        assertEquals(0, balance.compareTo(new BigDecimal(1000)));
    }

    @Test
    void getMoney() {
        ArgumentCaptor<BigDecimal> amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(cardsDao.getCardByNumber("1111"))
                .thenReturn(new Card(1L, "1111", 100L, TestUtil.getHash("0000")));

        when(accountService.getMoney(idCaptor.capture(), amountCaptor.capture()))
                .thenReturn(BigDecimal.TEN);

        cardService.getMoney("1111", "0000", BigDecimal.ONE);

        verify(accountService, only()).getMoney(anyLong(), any());
        assertEquals(BigDecimal.ONE, amountCaptor.getValue());
        assertEquals(100L, idCaptor.getValue().longValue());
    }

    @Test
    void putMoney() {
        String number = "1111";
        Long accountId = 1L;
        String pinCode = "0000";
        String hashedPinCode = TestUtil.getHash(pinCode);
        Card card = new Card(1L, number, accountId, hashedPinCode);
        when(cardsDao.getCardByNumber(number)).thenReturn(card);

        BigDecimal amountToPut = new BigDecimal("500.00");
        BigDecimal expectedBalanceAfterPut = new BigDecimal("1500.00");
        when(accountService.putMoney(accountId, amountToPut)).thenReturn(expectedBalanceAfterPut);

        BigDecimal result = cardService.putMoney(number, pinCode, amountToPut);

        assertEquals(0, result.compareTo(expectedBalanceAfterPut));
        verify(accountService).putMoney(accountId, amountToPut);
    }

    @Test
    void checkIncorrectPin() {
        String number = "1234";
        Long accountId = 1L;
        String pinCode = "0000";
        String hashedPinCode = TestUtil.getHash(pinCode);
        Card card = new Card(1L, number, accountId, hashedPinCode);
        when(cardsDao.getCardByNumber(number)).thenReturn(card);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cardService.getBalance(number, "0012");
        });
        assertEquals("Pincode is incorrect", exception.getMessage());
    }
}