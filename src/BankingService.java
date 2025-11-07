// BankingService.java
import java.util.*;

public class BankingService {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Customer> customersByUsername = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private StorageService storageService;
    
    public BankingService() {
        this.storageService = new StorageService(); 
        loadAllData();
    }
    
    // Load all data from files
    private void loadAllData() {
        // Load customers first
        List<Customer> loadedCustomers = storageService.loadCustomers();
        for (Customer customer : loadedCustomers) {
            customers.put(customer.getCustomerId(), customer);
            customersByUsername.put(customer.getUsername(), customer);
        }
        
        // Load accounts (requires customers to be loaded first)
        List<Account> loadedAccounts = storageService.loadAccounts(loadedCustomers);
        for (Account account : loadedAccounts) {
            accounts.put(account.getAccountNumber(), account);
        }
        
        // Load transactions
        List<Transaction> loadedTransactions = storageService.loadTransactions();
        transactions.addAll(loadedTransactions);
        
        System.out.println("Loaded " + customers.size() + " customers, " + 
                          accounts.size() + " accounts, " + 
                          transactions.size() + " transactions");
    }
    
    // Save all data to files
    private void saveAllData() {
        storageService.saveCustomers(new ArrayList<>(customers.values()));
        storageService.saveAccounts(new ArrayList<>(accounts.values()));
        storageService.saveTransactions(transactions);
    }
    
    // Customer management
    public boolean registerCustomer(Customer customer) {
        if (customers.containsKey(customer.getCustomerId()) || 
            customersByUsername.containsKey(customer.getUsername())) {
            return false;
        }
        customers.put(customer.getCustomerId(), customer);
        customersByUsername.put(customer.getUsername(), customer);
        saveAllData();
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
    public boolean accountExistsForCustomer(String customerId, String accountNumber) {
        for (Account account : accounts.values()) {
            if (account.getCustomerId().equals(customerId) && 
                account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean createAccount(Account account) {
        // Check if account number already exists globally
        if (accounts.containsKey(account.getAccountNumber())) {
            return false;
        }
        
        // Check if this customer already has this account number
        if (accountExistsForCustomer(account.getCustomerId(), account.getAccountNumber())) {
            return false;
        }
        
        Customer customer = customers.get(account.getCustomerId());
        if (customer != null) {
            account.setCustomer(customer);
            customer.addAccount(account);
        }
        
        accounts.put(account.getAccountNumber(), account);
        saveAllData();
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
            Customer customer = account.getCustomer();
            if (customer != null) {
                customer.getAccounts().remove(account);
            }
            boolean removed = accounts.remove(accountNumber) != null;
            if (removed) {
                saveAllData();
            }
            return removed;
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
                return false;
            }
        } else {
            return false;
        }
        
        String transactionId = "TXN" + System.currentTimeMillis();
        Transaction transaction = new Transaction(transactionId, accountNumber, transactionType, amount, new Date().toString());
        transactions.add(transaction);
        saveAllData();
        
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
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    // Authentication
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