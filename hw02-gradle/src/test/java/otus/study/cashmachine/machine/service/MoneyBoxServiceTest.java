package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;
import org.junit.jupiter.params.provider.CsvSource;
import otus.study.cashmachine.machine.service.MoneyBoxService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyBoxServiceTest {

    private MoneyBoxService moneyBoxService;
    private MoneyBox moneyBox;

    @BeforeEach
    void init() {
        moneyBoxService = new MoneyBoxServiceImpl();
        moneyBox = new MoneyBox();
    }

    @Test
    void charge7800() {
        int previousSum = moneyBoxService.checkSum(moneyBox);
        List<Integer> result = moneyBoxService.getMoney(moneyBox, 7800);
        assertEquals(7800, result.get(0) * 5000 + result.get(1) * 1000 + result.get(2) * 500 + result.get(3) * 100);
        assertEquals(previousSum - 7800, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void charge1001() {
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(moneyBox, 1001);
        });
        assertEquals("Can't charge the required sum", thrown.getMessage());
    }

    @Test
    void chargeMoreThanHave() {
        int illegalSumToCharge = moneyBoxService.checkSum(moneyBox) + 100;
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(moneyBox, illegalSumToCharge);
        });
        assertEquals("Not enough money", thrown.getMessage());
    }

    @Test
    void addNotes() {
        int initialSum = moneyBoxService.checkSum(moneyBox);
        moneyBoxService.putMoney(moneyBox, 1, 1, 1, 1);
        assertEquals(initialSum + 5000 + 1000 + 500 + 100, moneyBoxService.checkSum(moneyBox));
    }

    @Test
    void checkSum_initialMoneyBox_returnsCorrectSum() {
        int expectedSum = 100 * 1000 + 500 * 1000 + 1000 * 1000 + 5000 * 1000;
        int actualSum = moneyBoxService.checkSum(moneyBox);
        assertEquals(expectedSum, actualSum);
    }

    @Test
    void putMoney_addsNotesToMoneyBox() {
        int note100 = 5;
        int note500 = 3;
        int note1000 = 2;
        int note5000 = 1;

        moneyBoxService.putMoney(moneyBox, note100, note500, note1000, note5000);

        assertEquals(1000 + note100, moneyBox.getNote100());
        assertEquals(1000 + note500, moneyBox.getNote500());
        assertEquals(1000 + note1000, moneyBox.getNote1000());
        assertEquals(1000 + note5000, moneyBox.getNote5000());
    }

    @ParameterizedTest
    @CsvSource({"1500, 0, 1, 0, 0", "2000, 0, 0, 2, 0", "6000, 0, 0, 0, 1", "1600, 1, 1, 1, 0"})
    void getMoney_returnsCorrectNotes(int sum, int expected100, int expected500, int expected1000, int expected5000) {
        List<Integer> notes = moneyBoxService.getMoney(moneyBox, sum);
        assertEquals(expected100, notes.get(3));
        assertEquals(expected500, notes.get(2));
        assertEquals(expected1000, notes.get(1));
        assertEquals(expected5000, notes.get(0));
    }

    @Test
    void getMoney_notEnoughMoney_throwsException() {
        int sum = 100000000; // A sum that is definitely not available in the money box
        assertThrows(IllegalStateException.class, () -> moneyBoxService.getMoney(moneyBox, sum));
    }

    @Test
    void getMoney_sumNotDivisibleBy100_throwsException() {
        int sum = 1555; // This sum is not divisible by 100
        assertThrows(IllegalStateException.class, () -> moneyBoxService.getMoney(moneyBox, sum));
    }
}
