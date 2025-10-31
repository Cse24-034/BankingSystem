// StorageService.java
import java.io.*;
import java.util.*;

public class StorageService {
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    
    // Customer file operations
    public void saveCustomers(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers) {
                writer.println(customerToFileString(customer));
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        
        if (!file.exists()) {
            return customers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = customerFromFileString(line);
                if (customer != null) {
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }
    
    // Account file operations
    public void saveAccounts(List<Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts) {
                writer.println(accountToFileString(account));
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    
    public List<Account> loadAccounts(List<Customer> customers) {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        
        if (!file.exists()) {
            return accounts;
        }
        
        Map<String, Customer> customerMap = new HashMap<>();
        for (Customer customer : customers) {
            customerMap.put(customer.getCustomerId(), customer);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account account = accountFromFileString(line, customerMap);
                if (account != null) {
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }
    
    // Transaction file operations
    public void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.println(transactionToFileString(transaction));
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
    
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = transactionFromFileString(line);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    // Conversion methods
    private String customerToFileString(Customer customer) {
        return String.join("|",
            customer.getCustomerId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getUsername(),
            customer.getPassword(),
            customer.getPhone() != null ? customer.getPhone() : "",
            customer.getEmail() != null ? customer.getEmail() : "",
            customer.getCustomerType() != null ? customer.getCustomerType() : "Personal"
        );
    }
    
    private Customer customerFromFileString(String line) {
        try {
            String[] parts = line.split("\\|", -1); // -1 to keep trailing empty strings
            if (parts.length >= 8) {
                return new Customer(
                    parts[0], // customerId
                    parts[1], // firstName
                    parts[2], // lastName
                    parts[3], // username
                    parts[4], // password
                    parts[5].isEmpty() ? null : parts[5], // phone
                    parts[6].isEmpty() ? null : parts[6], // email
                    parts[7] // customerType
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing customer: " + line);
        }
        return null;
    }
    
    private String accountToFileString(Account account) {
        return String.join("|",
            account.getAccountNumber(),
            account.getCustomerId(),
            account.getAccountType(),
            String.valueOf(account.getBalance())
        );
    }
    
    private Account accountFromFileString(String line, Map<String, Customer> customerMap) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 4) {
                String accountNumber = parts[0];
                String customerId = parts[1];
                String accountType = parts[2];
                double balance = Double.parseDouble(parts[3]);
                
                Account account = new Account(accountNumber, customerId, accountType, balance);
                
                // Link to customer if exists
                Customer customer = customerMap.get(customerId);
                if (customer != null) {
                    account.setCustomer(customer);
                    customer.addAccount(account);
                }
                
                return account;
            }
        } catch (Exception e) {
            System.err.println("Error parsing account: " + line);
        }
        return null;
    }
    
    private String transactionToFileString(Transaction transaction) {
        return String.join("|",
            transaction.getTransactionId(),
            transaction.getAccountNumber(),
            transaction.getTransactionType(),
            String.valueOf(transaction.getAmount()),
            transaction.getDate()
        );
    }
    
    private Transaction transactionFromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 5) {
                return new Transaction(
                    parts[0], // transactionId
                    parts[1], // accountNumber
                    parts[2], // transactionType
                    Double.parseDouble(parts[3]), // amount
                    parts[4] // date
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing transaction: " + line);
        }
        return null;
    }
}