public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected String ownerId;   // customer ID

    public Account(String accountNumber, String branch, String ownerId, double openingBalance) {
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.ownerId = ownerId;
        this.balance = openingBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
        else System.out.println("Deposit must be positive.");
    }

    public double getBalance() {
        return balance;
    }

    public abstract void getAccountDetails();
}
