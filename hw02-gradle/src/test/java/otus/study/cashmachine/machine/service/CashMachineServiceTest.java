package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static otus.study.cashmachine.TestUtil.getHash;


@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Mock
    private CardService cardService;

    @Mock
    private MoneyBoxService moneyBoxService;


    @InjectMocks
    private CashMachineServiceImpl cashMachineService;

    @Mock
    private CashMachine cashMachine;


    @BeforeEach
    void init() {
        cashMachine = new CashMachine(new MoneyBox());
    }

    @Test
    void getMoney() {
        String cardNumber = "1234567890123456";
        String pin = "1234";
        BigDecimal amount = new BigDecimal("500.00");
        List<Integer> expectedNotes = Arrays.asList(1, 0, 1, 0);

        when(cardService.getMoney(cardNumber, pin, amount)).thenReturn(amount);
        when(moneyBoxService.getMoney(cashMachine.getMoneyBox(), amount.intValue())).thenReturn(expectedNotes);

        List<Integer> actualNotes = cashMachineService.getMoney(cashMachine, cardNumber, pin, amount);

        assertEquals(expectedNotes, actualNotes);
        verify(cardService).getMoney(cardNumber, pin, amount);
        verify(moneyBoxService).getMoney(cashMachine.getMoneyBox(), amount.intValue());
    }

    @Test
    void putMoney() {
        String number = "8657";
        String pin = "7858";
        List<Integer> notes = List.of(1, 2, 3, 4);
        BigDecimal amount = new BigDecimal(5100);
        CashMachine machine = new CashMachine(new MoneyBox(1, 0, 0, 1));
        Card card = new Card(1, number, 1L, getHash(pin));

        when(cardService.putMoney(eq(number), eq(pin), any())).thenReturn(amount);
        
        doNothing().when(moneyBoxService).putMoney(any(), anyInt(), anyInt(), anyInt(), anyInt());

        BigDecimal expectedPutMoneyAmount = new BigDecimal(8900);
        when(cardService.putMoney(number, pin, expectedPutMoneyAmount)).thenReturn(expectedPutMoneyAmount);

        BigDecimal addMoney = cashMachineService.putMoney(machine, number, pin, notes);

        assertEquals(addMoney, expectedPutMoneyAmount);
        verify(cardService).getBalance(number, pin);
    }
    @Test
    void checkBalance() {
        String cardNumber = "1234567890123456";
        String pin = "1234";
        BigDecimal expectedBalance = new BigDecimal("5000.00"); // Example expected balance

        when(cardService.getBalance(cardNumber, pin)).thenReturn(expectedBalance);

        BigDecimal actualBalance = cashMachineService.checkBalance(cashMachine, cardNumber, pin);

        assertEquals(expectedBalance, actualBalance);
        verify(cardService).getBalance(cardNumber, pin);
    }

    @Test
    void changePin() {
        String cardNumber = "1234567890123456";
        String oldPin = "1234";
        String newPin = "5678";

        when(cardService.cnangePin(cardNumber, oldPin, newPin)).thenReturn(true);

        boolean result = cashMachineService.changePin(cardNumber, oldPin, newPin);

        assertTrue(result);
        verify(cardService).cnangePin(cardNumber, oldPin, newPin);
    }

    @Test
    void changePinWithAnswer() {
        String cardNumber = "1234567890123456";
        String oldPin = "1234";
        String newPin = "5678";

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[1].equals(oldPin) && args[2].equals(newPin);
        }).when(cardService).cnangePin(anyString(), anyString(), anyString());

        boolean result = cashMachineService.changePin(cardNumber, oldPin, newPin);

        assertTrue(result);
        verify(cardService).cnangePin(cardNumber, oldPin, newPin);
    }
}