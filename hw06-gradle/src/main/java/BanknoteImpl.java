public class BanknoteImpl implements Banknote {
    private final int denomination;

    public BanknoteImpl(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public int getDenomination() {
        return denomination;
    }
}
