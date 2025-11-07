// IntegrationTest.java
import java.util.List;

public class IntegrationTest {
    
    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM INTEGRATION TEST ===");
        System.out.println();
        
        // Test 1: Complete User Flow
        testCompleteUserFlow();
        
        // Test 2: Data Persistence
        testDataPersistence();
        
        // Test 3: Error Handling
        testErrorHandling();
        
        System.out.println("=== INTEGRATION TEST COMPLETED ===");
    }
    
    public static void testCompleteUserFlow() {
        System.out.println("TEST 1: COMPLETE USER FLOW");
        System.out.println("---------------------------");
        
        BankingService bankingService = new BankingService();
        
        // Step 1: Register Customer
        System.out.println("Step 1: Registering new customer...");
        Customer customer = new Customer("TEST001", "Integration", "Test", "integration_test", 
                                       "password123", "555-0001", "test@integration.com", "Personal");
        boolean registered = bankingService.registerCustomer(customer);
        System.out.println("Customer registration: " + (registered ? "SUCCESS" : "FAILED"));
        
        // Step 2: Create Account
        System.out.println("Step 2: Creating account for customer...");
        Account account = new Account("ACC-TEST-001", "TEST001", "Savings", 1000.00);
        boolean accountCreated = bankingService.createAccount(account);
        System.out.println("Account creation: " + (accountCreated ? "SUCCESS" : "FAILED"));
        
        // Step 3: Authenticate User
        System.out.println("Step 3: Authenticating user...");
        Customer authenticated = bankingService.getAuthenticatedCustomer("integration_test", "password123");
        System.out.println("User authentication: " + (authenticated != null ? "SUCCESS" : "FAILED"));
        
        // Step 4: Process Transactions
        System.out.println("Step 4: Processing transactions...");
        boolean depositSuccess = bankingService.processTransaction("ACC-TEST-001", "Deposit", 500.00);
        System.out.println("Deposit transaction: " + (depositSuccess ? "SUCCESS" : "FAILED"));
        
        boolean withdrawalSuccess = bankingService.processTransaction("ACC-TEST-001", "Withdrawal", 200.00);
        System.out.println("Withdrawal transaction: " + (withdrawalSuccess ? "SUCCESS" : "FAILED"));
        
        // Step 5: Verify Results
        System.out.println("Step 5: Verifying results...");
        Account finalAccount = bankingService.getAccount("ACC-TEST-001");
        if (finalAccount != null) {
            System.out.println("Final balance: $" + finalAccount.getBalance());
            System.out.println("Expected balance: $1300.00");
            System.out.println("Balance verification: " + 
                (Math.abs(finalAccount.getBalance() - 1300.00) < 0.01 ? "SUCCESS" : "FAILED"));
        }
        
        List<Transaction> transactions = bankingService.getTransactionHistory("ACC-TEST-001");
        System.out.println("Number of transactions recorded: " + transactions.size());
        
        System.out.println("COMPLETE USER FLOW TEST: " + 
            (registered && accountCreated && authenticated != null && depositSuccess && 
             withdrawalSuccess && finalAccount != null && finalAccount.getBalance() == 1300.00 ? 
             "PASSED" : "FAILED"));
        System.out.println();
    }
    
    public static void testDataPersistence() {
        System.out.println("TEST 2: DATA PERSISTENCE");
        System.out.println("-------------------------");
        
        // Create first service instance and add data
        BankingService service1 = new BankingService();
        Customer customer = new Customer("PERSIST001", "Persistence", "Test", "persist_user", 
                                       "pass123", null, null, "Business");
        service1.registerCustomer(customer);
        service1.createAccount(new Account("ACC-PERSIST-001", "PERSIST001", "Checking", 2500.00));
        
        // Create second service instance to simulate application restart
        BankingService service2 = new BankingService();
        
        // Verify data persisted
        Customer persistedCustomer = service2.getCustomer("PERSIST001");
        Account persistedAccount = service2.getAccount("ACC-PERSIST-001");
        
        System.out.println("Customer persistence: " + (persistedCustomer != null ? "SUCCESS" : "FAILED"));
        System.out.println("Account persistence: " + (persistedAccount != null ? "SUCCESS" : "FAILED"));
        System.out.println("Account balance persistence: " + 
            (persistedAccount != null && persistedAccount.getBalance() == 2500.00 ? "SUCCESS" : "FAILED"));
        
        System.out.println("DATA PERSISTENCE TEST: " + 
            (persistedCustomer != null && persistedAccount != null && 
             persistedAccount.getBalance() == 2500.00 ? "PASSED" : "FAILED"));
        System.out.println();
    }
    
    public static void testErrorHandling() {
        System.out.println("TEST 3: ERROR HANDLING");
        System.out.println("-----------------------");
        
        BankingService bankingService = new BankingService();
        
        // Test duplicate customer registration
        Customer customer1 = new Customer("DUPE001", "First", "User", "user1", "pass1", null, null, "Personal");
        Customer customer2 = new Customer("DUPE001", "Second", "User", "user2", "pass2", null, null, "Business");
        
        boolean firstRegistration = bankingService.registerCustomer(customer1);
        boolean secondRegistration = bankingService.registerCustomer(customer2);
        
        System.out.println("Duplicate customer ID prevention: " + 
            (firstRegistration && !secondRegistration ? "SUCCESS" : "FAILED"));
        
        // Test insufficient funds
        bankingService.createAccount(new Account("ACC-LOW-001", "DUPE001", "Savings", 50.00));
        boolean insufficientWithdrawal = bankingService.processTransaction("ACC-LOW-001", "Withdrawal", 100.00);
        Account lowAccount = bankingService.getAccount("ACC-LOW-001");
        
        System.out.println("Insufficient funds prevention: " + 
            (!insufficientWithdrawal && lowAccount.getBalance() == 50.00 ? "SUCCESS" : "FAILED"));
        
        // Test invalid authentication
        Customer invalidAuth = bankingService.getAuthenticatedCustomer("nonexistent", "wrongpass");
        System.out.println("Invalid authentication handling: " + 
            (invalidAuth == null ? "SUCCESS" : "FAILED"));
        
        System.out.println("ERROR HANDLING TEST: " + 
            (firstRegistration && !secondRegistration && !insufficientWithdrawal && 
             lowAccount.getBalance() == 50.00 && invalidAuth == null ? "PASSED" : "FAILED"));
        System.out.println();
    }
}