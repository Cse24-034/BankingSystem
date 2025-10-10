public class Investment extends Account implements Withdrawable, InterestBearing {
    private static final double INTEREST_RATE = 0.05;  // 5% monthly
    private static final double MIN_DEPOSIT = 500.00;

    public Investment(String accNo, String branch, String ownerId, double openingDeposit) {
        super(accNo, branch, ownerId, openingDeposit >= MIN_DEPOSIT ? openingDeposit : MIN_DEPOSIT);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
        else System.out.println("Invalid withdrawal amount.");
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
        System.out.println("Investment Account " + accountNumber + " | Balance: " + balance);
    }
}
