import java.util.HashMap;
import java.util.Map;

public class BanknoteStorageImpl implements BanknoteStorage {
    private final Map<Banknote, Integer> banknotes;

    public BanknoteStorageImpl() {
        this.banknotes = new HashMap<>();
    }

    @Override
    public void addBanknotes(Banknote banknote, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество банкнот должно быть доступным");
        }
        banknotes.put(banknote, banknotes.getOrDefault(banknote, 0) + count);
    }

    @Override
    public Map<Banknote, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Запрашиваемая сумма должна быть доступной");
        }
        if (amount > getTotalAmount()) {
            throw new InsufficientFundsException("В банкомате недостаточно денег для выдачи запрошенной суммы");
        }

        Map<Banknote, Integer> result = new HashMap<>();
        int remainingAmount = amount;

        for (Map.Entry<Banknote, Integer> entry : banknotes.entrySet()) {
            Banknote banknote = entry.getKey();
            int count = Math.min(remainingAmount / banknote.getDenomination(), entry.getValue());
            if (count > 0) {
                result.put(banknote, count);
                remainingAmount -= banknote.getDenomination() * count;
            }
        }

        if (remainingAmount > 0) {
            throw new UnableToDispenseException("Не удается выдать запрашиваемую сумму доступными банкнотами");
        }

        for (Map.Entry<Banknote, Integer> entry : result.entrySet()) {
            banknotes.put(entry.getKey(), banknotes.get(entry.getKey()) - entry.getValue());
        }

        return result;
    }

    @Override
    public int getTotalAmount() {
        return banknotes.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getDenomination() * entry.getValue())
                .sum();
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class UnableToDispenseException extends RuntimeException {
        public UnableToDispenseException(String message) {
            super(message);
        }
    }
}