package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MoneyBoxServiceTest {

    private MoneyBoxService moneyBoxService;

    private MoneyBox moneyBox;

    @BeforeEach
    void init() {
        moneyBoxService = new MoneyBoxServiceImpl();
        moneyBox = new MoneyBox(10, 10, 10, 10);
    }

    @Test
    void charge7800() {
        int previousSum = moneyBoxService.checkSum(moneyBox);
        List<Integer> result = moneyBoxService.getMoney(moneyBox, 7800);
        int actualSum = result.get(0) * 5000 + result.get(1) * 1000 + result.get(2) * 500 + result.get(3) * 100;
        assertEquals(7800, actualSum);
        assertEquals(previousSum - 7800, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void charge1001() {
        assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(moneyBox, 1001);
        });
    }

    @Test
    void chargeMoreThanHave() {
        int illegalSumToCharge = moneyBoxService.checkSum(moneyBox) + 100;
        assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(moneyBox, illegalSumToCharge);
        });
    }

    @Test
    void addNotes() {
        int initialSum = moneyBoxService.checkSum(moneyBox);
        moneyBoxService.putMoney(moneyBox, 1, 1, 1, 1);
        assertEquals(initialSum + 5000 + 1000 + 500 + 100, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void checkSum_returnsCorrectSum() {
        int expectedSum = 10 * 5000 + 10 * 1000 + 10 * 500 + 10 * 100;
        assertEquals(expectedSum, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void putMoney_addsNotesToMoneyBox() {
        int initialSum = moneyBoxService.checkSum(moneyBox);
        moneyBoxService.putMoney(moneyBox, 5, 3, 2, 1);
        int expectedSum = initialSum + 5 * 100 + 3 * 500 + 2 * 1000 + 1 * 5000;
        assertEquals(expectedSum, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void getMoney_chargesCorrectSum() {
        int chargeSum = 5000 + 1000 + 500 + 100;
        List<Integer> expectedNotes = List.of(1, 1, 1, 1);
        List<Integer> result = moneyBoxService.getMoney(moneyBox, chargeSum);
        assertEquals(expectedNotes, result);
    }

    @Test
    void getMoney_throwsExceptionForInsufficientNotes() {
        int chargeSum = 50;
        assertThrows(IllegalStateException.class, () -> moneyBoxService.getMoney(moneyBox, chargeSum));
    }

    @Test
    void getMoney_throwsExceptionForNonMultipleAmount() {
        int chargeSum = 5555;
        assertThrows(IllegalStateException.class, () -> moneyBoxService.getMoney(moneyBox, chargeSum));
    }

    @Test
    void getMoney_throwsExceptionForNullMoneyBox() {
        int chargeSum = 1000;
        assertThrows(NullPointerException.class, () -> moneyBoxService.getMoney(null, chargeSum));
    }
}