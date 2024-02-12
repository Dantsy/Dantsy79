import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest2 {

    private ATM atm;
    private BanknoteStorage banknoteStorage;

    @BeforeEach
    void setUp() {
        banknoteStorage = new BanknoteStorageImpl();
        atm = new ATM(banknoteStorage);
    }

    @Test
    void testAddBanknotes() {
        Banknote banknote100 = new BanknoteImpl(100);
        atm.addBanknotes(banknote100, 5);
        assertEquals(500, atm.getTotalAmount());
    }

    @Test
    void testInsufficientFunds() {
        Banknote banknote100 = new BanknoteImpl(100);
        atm.addBanknotes(banknote100, 5);

        assertThrows(BanknoteStorageImpl.InsufficientFundsException.class, () -> atm.withdraw(600));
    }
}
