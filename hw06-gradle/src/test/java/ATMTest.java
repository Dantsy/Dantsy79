import java.util.Map;

public class ATMTest {
    public static void main(String[] args) {
        BanknoteStorage banknoteStorage = new BanknoteStorageImpl();
        ATM atm = new ATM(banknoteStorage);

        Banknote banknote100 = new BanknoteImpl(100);
        Banknote banknote50 = new BanknoteImpl(50);
        Banknote banknote20 = new BanknoteImpl(20);

        atm.addBanknotes(banknote100, 5);
        atm.addBanknotes(banknote50, 10);
        atm.addBanknotes(banknote20, 20);

        System.out.println("Общая сумма: " + atm.getTotalAmount());

        try {
            Map<Banknote, Integer> withdrawn = atm.withdraw(1700);
            System.out.println("Снято: " + withdrawn);
            System.out.println("Осталось на счете: " + atm.getTotalAmount());
        } catch (BanknoteStorageImpl.InsufficientFundsException | BanknoteStorageImpl.UnableToDispenseException e) {
            System.out.println(e.getMessage());
        }

        try {
            atm.withdraw(3301);
        } catch (BanknoteStorageImpl.InsufficientFundsException | BanknoteStorageImpl.UnableToDispenseException e) {
            System.out.println(e.getMessage());
        }
    }
}