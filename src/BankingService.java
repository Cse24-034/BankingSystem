// BankingService.java
import java.util.*;

public class BankingService {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Customer> customersByUsername = new HashMap<>(); // For login
    private List<Transaction> transactions = new ArrayList<>();
    
    // Customer management
    public boolean registerCustomer(Customer customer) {
        if (customers.containsKey(customer.getCustomerId()) || 
            customersByUsername.containsKey(customer.getUsername())) {
            return false; // Customer ID or username already exists
        }
        customers.put(customer.getCustomerId(), customer);
        customersByUsername.put(customer.getUsername(), customer);
        return true;
    }
    
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    public Customer getCustomerByUsername(String username) {
        return customersByUsername.get(username);
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    // Account management
    public boolean createAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return false; // Account number already exists
        }
        
        // Link account to customer
        Customer customer = customers.get(account.getCustomerId());
        if (customer != null) {
            account.setCustomer(customer);
            customer.addAccount(account);
        }
        
        accounts.put(account.getAccountNumber(), account);
        return true;
    }
    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    public List<Account> getCustomerAccounts(String customerId) {
        List<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getCustomerId().equals(customerId)) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }
    
    public boolean deleteAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            // Remove from customer's account list
            Customer customer = account.getCustomer();
            if (customer != null) {
                customer.getAccounts().remove(account);
            }
            return accounts.remove(accountNumber) != null;
        }
        return false;
    }
    
    // Transaction processing
    public boolean processTransaction(String accountNumber, String transactionType, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) return false;
        
        if ("Deposit".equals(transactionType)) {
            account.deposit(amount);
        } else if ("Withdrawal".equals(transactionType)) {
            if (!account.withdraw(amount)) {
                return false; // Insufficient funds
            }
        } else {
            return false; // Invalid transaction type
        }
        
        // Record transaction
        String transactionId = "TXN" + System.currentTimeMillis();
        Transaction transaction = new Transaction(transactionId, accountNumber, transactionType, amount, new Date().toString());
        transactions.add(transaction);
        
        return true;
    }
    
    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }
    
    // Authentication - Now uses registered customers
    public boolean authenticateUser(String username, String password) {
        Customer customer = customersByUsername.get(username);
        return customer != null && customer.validateCredentials(username, password);
    }
    
    public Customer getAuthenticatedCustomer(String username, String password) {
        Customer customer = customersByUsername.get(username);
        if (customer != null && customer.validateCredentials(username, password)) {
            return customer;
        }
        return null;
    }
}