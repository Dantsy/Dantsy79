package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import otus.study.cashmachine.bank.data.Card;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Spy
    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private CashMachine cashMachine = new CashMachine(new MoneyBox());

    @BeforeEach
    void init() {
        cashMachine = new CashMachine(new MoneyBox());
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }

    @Test
    void testgetMoney() {
        String cardNum = "1234567890";
        String pin = "1234";
        BigDecimal amount = new BigDecimal("500.00");
        List<Integer> expectedNotes = Arrays.asList(1, 2, 1, 0);

        when(cardService.getMoney(cardNum, pin, amount)).thenReturn(amount);
        when(moneyBoxService.getMoney(cashMachine.getMoneyBox(), amount.intValue())).thenReturn(expectedNotes);

        List<Integer> actualNotes = cashMachineService.getMoney(cashMachine, cardNum, pin, amount);

        assertEquals(expectedNotes, actualNotes);
        verify(cardService).getMoney(cardNum, pin, amount);
        verify(moneyBoxService).getMoney(cashMachine.getMoneyBox(), amount.intValue());
    }

    @Test
    void testPutMoney() {
        String cardNum = "1234567890";
        String pin = "1234";
        List<Integer> notes = Arrays.asList(1, 2, 1, 0);
        BigDecimal expectedBalance = new BigDecimal("6500.00");
        BigDecimal expectedAmount = new BigDecimal(notes.get(3) * 100 + notes.get(2) * 500 + notes.get(1) * 1000 + notes.get(0) * 5000);

        doNothing().when(moneyBoxService).putMoney(cashMachine.getMoneyBox(), notes.get(3), notes.get(2), notes.get(1), notes.get(0));
        when(cardService.putMoney(cardNum, pin, expectedAmount)).thenReturn(expectedBalance);

        BigDecimal actualBalance = cashMachineService.putMoney(cashMachine, cardNum, pin, notes);

        assertEquals(expectedBalance, actualBalance);
        verify(moneyBoxService).putMoney(cashMachine.getMoneyBox(), notes.get(3), notes.get(2), notes.get(1), notes.get(0));
        verify(cardService).putMoney(cardNum, pin, expectedAmount);
    }

    @Test
    void checkBalance() {
        String cardNum = "1234567890";
        String pin = "1234";
        BigDecimal expectedBalance = new BigDecimal("1000.00");

        when(cardService.getBalance(cardNum, pin)).thenReturn(expectedBalance);

        BigDecimal actualBalance = cashMachineService.checkBalance(cashMachine, cardNum, pin);

        assertEquals(expectedBalance, actualBalance);
        verify(cardService).getBalance(cardNum, pin);
    }

    @Test
    void changePin() {
        String cardNum = "1234567890";
        String oldPin = "1234";
        String newPin = "5678";

        when(cardService.cnangePin(cardNum, oldPin, newPin)).thenReturn(true);

        boolean result = cashMachineService.changePin(cardNum, oldPin, newPin);

        assertTrue(result);
        verify(cardService).cnangePin(cardNum, oldPin, newPin);
    }


    @Test
    void changePinWithAnswer() {
        String cardNum = "1234567890";
        String oldPin = "1234";
        String newPin = "5678";

        doAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            card.setPinCode("newHashedPin");
            return true;
        }).when(cardService).cnangePin(anyString(), anyString(), anyString());

        boolean result = cashMachineService.changePin(cardNum, oldPin, newPin);

        assertTrue(result);
        verify(cardService).cnangePin(cardNum, oldPin, newPin);
    }
}