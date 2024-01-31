package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
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


@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Mock
    private CardService cardService;

    @Mock
    private MoneyBoxService moneyBoxService;

    @Mock
    private MoneyBox moneyBox;

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

        // Mock the behavior of the cardService and moneyBoxService
        when(cardService.getMoney(cardNumber, pin, amount)).thenReturn(amount);
        when(moneyBoxService.getMoney(cashMachine.getMoneyBox(), amount.intValue())).thenReturn(expectedNotes);

        // Call the method under test
        List<Integer> actualNotes = cashMachineService.getMoney(cashMachine, cardNumber, pin, amount);

        // Verify the results
        assertEquals(expectedNotes, actualNotes);
        verify(cardService).getMoney(cardNumber, pin, amount);
        verify(moneyBoxService).getMoney(cashMachine.getMoneyBox(), amount.intValue());
    }

    @Test
    void checkBalance() {
        String cardNumber = "1234567890123456";
        String pin = "1234";
        BigDecimal expectedBalance = new BigDecimal("5000.00"); // Example expected balance

        // Mock the behavior of the cardService
        when(cardService.getBalance(cardNumber, pin)).thenReturn(expectedBalance);

        // Call the method under test
        BigDecimal actualBalance = cashMachineService.checkBalance(cashMachine, cardNumber, pin);

        // Verify the results
        assertEquals(expectedBalance, actualBalance);
        verify(cardService).getBalance(cardNumber, pin);
    }

    @Test
    void changePin() {
        String cardNumber = "1234567890123456";
        String oldPin = "1234";
        String newPin = "5678";

        // Mock the behavior of the cardService
        when(cardService.cnangePin(cardNumber, oldPin, newPin)).thenReturn(true);

        // Call the method under test
        boolean result = cashMachineService.changePin(cardNumber, oldPin, newPin);

        // Verify the results
        assertTrue(result);
        verify(cardService).cnangePin(cardNumber, oldPin, newPin);
    }

    @Test
    void changePinWithAnswer() {
        String cardNumber = "1234567890123456";
        String oldPin = "1234";
        String newPin = "5678";

        // Mock the behavior of the cardService
        doAnswer(invocation -> {
            // Custom logic for the answer
            Object[] args = invocation.getArguments();
            return args[1].equals(oldPin) && args[2].equals(newPin);
        }).when(cardService).cnangePin(anyString(), anyString(), anyString());

        // Call the method under test
        boolean result = cashMachineService.changePin(cardNumber, oldPin, newPin);

        // Verify the results
        assertTrue(result);
        verify(cardService).cnangePin(cardNumber, oldPin, newPin);
    }
}