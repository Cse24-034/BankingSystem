public class Savings extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public Savings(String accNo, String branch, String ownerId, double openingBalance) {
        super(accNo, branch, ownerId, openingBalance);
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
    }

    @Override
    public void applyMonthlyInterest() {
        calculateMonthlyInterest();
    }

    @Override
    public void getAccountDetails() {
        System.out.println("Savings Account " + accountNumber + " | Balance: " + balance);
    }

    public void withdraw(double amount) {
        System.out.println("Withdrawals not allowed on Savings account.");
    }
}
