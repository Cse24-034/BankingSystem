public class Cheque extends Account implements Withdrawable {
    private String employerName;
    private String employerAddress;

    public Cheque(String accNo, String branch, String ownerId, String empName, String empAddr, double openingBalance) {
        super(accNo, branch, ownerId, openingBalance);
        this.employerName = empName;
        this.employerAddress = empAddr;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
        else System.out.println("Invalid withdrawal amount.");
    }

    @Override
    public void getAccountDetails() {
        System.out.println("Cheque Account " + accountNumber + " | Balance: " + balance);
    }
}
