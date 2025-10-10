public class Transaction {
    private String transactionId;
    private String type;
    private double amount;

    public Transaction(String id, String type, double amount) {
        this.transactionId = id;
        this.type = type;
        this.amount = amount;
    }

    public void execute(Account account) {
        if (type.equalsIgnoreCase("deposit")) account.deposit(amount);
        else if (type.equalsIgnoreCase("withdraw") && account instanceof Withdrawable)
            ((Withdrawable) account).withdraw(amount);
        else System.out.println("Invalid transaction type.");
    }
}
