import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BanknoteStorage banknoteStorage = new BanknoteStorageImpl();

        ATM atm = new ATM(banknoteStorage);

        Banknote banknote100 = new BanknoteImpl(100);
        banknoteStorage.addBanknotes(banknote100, 5);

        try {
            Map<Banknote, Integer> withdrawn = atm.withdraw(300);
            System.out.println("Снято: " + withdrawn);
        } catch (BanknoteStorageImpl.InsufficientFundsException e) {
            System.out.println("Недостаточно средств на счете.");
        } catch (BanknoteStorageImpl.UnableToDispenseException e) {
            System.out.println("Невозможно выдать запрашиваемую сумму.");
        }
    }
}