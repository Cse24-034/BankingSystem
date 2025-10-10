public class Bank {
    private String bankName;
    private String branchCode;

    public Bank(String bankName, String branchCode) {
        this.bankName = bankName;
        this.branchCode = branchCode;
    }

    public void openAccount(Account account) {
        System.out.println("Account " + account.accountNumber + " opened at " + bankName);
    }
}
