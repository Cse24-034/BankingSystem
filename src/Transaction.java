// Transaction.java
public class Transaction {
    private String transactionId;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private String date;
    
    public Transaction(String transactionId, String accountNumber, String transactionType, 
                      double amount, String date) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }
    
    // Getters
    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}