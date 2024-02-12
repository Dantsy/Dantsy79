import java.util.Map;

public class ATM {
    private final BanknoteStorage banknoteStorage;

    public ATM(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    public void addBanknotes(Banknote banknote, int count) {
        banknoteStorage.addBanknotes(banknote, count);
    }

    public Map<Banknote, Integer> withdraw(int amount) {
        return banknoteStorage.withdraw(amount);
    }

    public int getTotalAmount() {
        return banknoteStorage.getTotalAmount();
    }
}
