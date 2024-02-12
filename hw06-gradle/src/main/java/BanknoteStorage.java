import java.util.Map;

public interface BanknoteStorage {
    void addBanknotes(Banknote banknote, int count);

    Map<Banknote, Integer> withdraw(int amount);

    int getTotalAmount();
}
