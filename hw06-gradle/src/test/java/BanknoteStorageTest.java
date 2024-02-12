import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BanknoteStorageTest {
    private BanknoteStorage banknoteStorage;

    @BeforeEach
    void setUp() {
        banknoteStorage = new BanknoteStorageImpl();
    }

    @Test
    void testAddBanknotes() {
        Banknote banknote100 = new BanknoteImpl(200);
        banknoteStorage.addBanknotes(banknote100, 5);
        assertEquals(1000, banknoteStorage.getTotalAmount());
    }

    @Test
    void testWithdraw() {
        Banknote banknote100 = new BanknoteImpl(100);
        banknoteStorage.addBanknotes(banknote100, 5);

        Map<Banknote, Integer> withdrawn = banknoteStorage.withdraw(300);
        assertEquals(3, withdrawn.get(banknote100).intValue());
        assertEquals(200, banknoteStorage.getTotalAmount());
    }

    @Test
    void testInsufficientFunds() {
        Banknote banknote100 = new BanknoteImpl(100);
        banknoteStorage.addBanknotes(banknote100, 5);

        assertThrows(BanknoteStorageImpl.InsufficientFundsException.class, () -> banknoteStorage.withdraw(600));
    }

    @Test
    void testUnableToDispense() {
        Banknote banknote100 = new BanknoteImpl(100);
        banknoteStorage.addBanknotes(banknote100, 5);

        assertThrows(BanknoteStorageImpl.InsufficientFundsException.class, () -> banknoteStorage.withdraw(3301));
    }
}
