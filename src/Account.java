// Account.java
public class Account {
    private String accountNumber;
    private String customerId;
    private String accountType;
    private double balance;
    private Customer customer;
    
    public Account(String accountNumber, String customerId, String accountType, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = initialBalance;
    }
    
    // Getters and setters
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}