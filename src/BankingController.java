// BankingController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.List;

public class BankingController {
    
    // TabPane and Tabs
    @FXML private TabPane mainTabPane;
    @FXML private Tab loginTab;
    @FXML private Tab customerTab;
    @FXML private Tab accountTab;
    @FXML private Tab transactionTab;
    @FXML private Tab transactionHistoryTab;
    
    // Transaction History Tab - Fix: Use specific type instead of wildcard
    @FXML private TableView<String> transactionHistoryTable;
    @FXML private Button closeHistoryButton;

    // Transaction Tab
    @FXML private ComboBox<String> accountComboBox;
    @FXML private ComboBox<String> transactionTypeComboBox;
    @FXML private TextField amountField;
    @FXML private TextField balanceField;
    @FXML private Button cancelTransactionButton;
    @FXML private Button continueTransactionButton;

    // Account Tab
    @FXML private TextField accountNumberField;
    @FXML private TextField customerIdField;
    @FXML private TextField accountTypeField;
    @FXML private TextField initialBalanceField;
    @FXML private TableView<String> accountsTableView;
    @FXML private Button createAccountButton;
    @FXML private Button deleteAccountButton;

    // Customer Tab
    @FXML private TextField txtCustomerId;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private TableView<String> customersTableView;
    @FXML private Button registerCustomerButton;

    // Login Tab
    @FXML private TextField loginUsernameField;
    @FXML private TextField loginPasswordField;
    @FXML private Button loginButton;
    
    private BankingService bankingService;
    private ObservableList<String> accountsData;
    private ObservableList<String> customersData;
    private ObservableList<String> transactionHistoryData;
    private Customer currentCustomer; // Track logged-in customer

    public BankingController() {
        this.bankingService = new BankingService();
        // Sample data initialization removed
    }
    
    // Add this method to initialize the login tab
    public void initializeLoginTab() {
        if (mainTabPane != null && loginTab != null) {
            // Select the login tab when application starts
            mainTabPane.getSelectionModel().select(loginTab);
            System.out.println("Application started on Login tab");
        }
    }
    
    // Removed initializeSampleData() method entirely

    // === TRANSACTION HISTORY METHODS ===
    @FXML
    void handleCloseHistory(ActionEvent event) {
        showSuccessAlert("History tab closed successfully");
    }

    // === TRANSACTION TAB METHODS ===
    @FXML
    void handleContinueTransaction(ActionEvent event) {
        String accountNumber = accountComboBox.getValue();
        String transactionType = transactionTypeComboBox.getValue();
        String amountStr = amountField.getText();
        
        if (accountNumber == null || transactionType == null || amountStr.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountStr);
            
            // Delegate to service layer
            boolean success = bankingService.processTransaction(accountNumber, transactionType, amount);
            
            if (success) {
                updateBalanceDisplay(accountNumber);
                clearTransactionFields();
                showSuccessAlert("Transaction completed successfully");
                refreshAccountsTable();
                refreshTransactionHistory(); // Refresh transaction history after successful transaction
            } else {
                showAlert("Error", "Transaction failed - insufficient funds or invalid amount");
            }
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Amount must be a valid number");
        }
    }

    @FXML
    void handleCancelTransaction(ActionEvent event) {
        clearTransactionFields();
    }

    private void updateBalanceDisplay(String accountNumber) {
        Account account = bankingService.getAccount(accountNumber);
        if (account != null) {
            balanceField.setText(String.format("$%.2f", account.getBalance()));
        }
    }

    // === ACCOUNT TAB METHODS ===
    @FXML
    void handleCreateAccount(ActionEvent event) {
        String accountNumber = accountNumberField.getText();
        String customerId = customerIdField.getText();
        String accountType = accountTypeField.getText();
        String initialBalanceStr = initialBalanceField.getText();
        
        if (accountNumber.isEmpty() || customerId.isEmpty() || accountType.isEmpty() || initialBalanceStr.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }
        
        // Check if customer exists
        Customer customer = bankingService.getCustomer(customerId);
        if (customer == null) {
            showAlert("Error", "Customer ID not found. Please register customer first.");
            return;
        }
        
        try {
            double initialBalance = Double.parseDouble(initialBalanceStr);
            
            // Delegate to service layer
            Account account = new Account(accountNumber, customerId, accountType, initialBalance);
            boolean success = bankingService.createAccount(account);
            
            if (success) {
                refreshAccountsTable();
                refreshAccountComboBox();
                clearAccountFields();
                showSuccessAlert("Account created successfully for customer: " + customer.getFullName());
            } else {
                showAlert("Error", "Account creation failed - account number may already exist");
            }
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Initial balance must be a valid number");
        }
    }

    @FXML
    void handleDeleteAccount(ActionEvent event) {
        String selectedAccountInfo = accountsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedAccountInfo != null) {
            // Extract account number from the displayed string
            String accountNumber = extractAccountNumber(selectedAccountInfo);
            
            // Delegate to service layer
            boolean success = bankingService.deleteAccount(accountNumber);
            
            if (success) {
                refreshAccountsTable();
                refreshAccountComboBox();
                refreshTransactionHistory(); // Refresh transaction history after account deletion
                showSuccessAlert("Account deleted successfully");
            } else {
                showAlert("Error", "Failed to delete account");
            }
        } else {
            showAlert("Error", "Please select an account to delete");
        }
    }

    // === CUSTOMER TAB METHODS ===
    @FXML
    void handleRegisterCustomer(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String customerType = cmbCustomerType.getValue();
        
        if (customerId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill required fields");
            return;
        }
        
        // Delegate to service layer
        Customer customer = new Customer(customerId, firstName, lastName, username, password, phone, email, customerType);
        boolean success = bankingService.registerCustomer(customer);
        
        if (success) {
            refreshCustomersTable();
            clearCustomerFields();
            showSuccessAlert("Customer registered successfully. You can now login with username: " + username);
        } else {
            showAlert("Error", "Customer registration failed - Customer ID or Username may already exist");
        }
    }

    // === LOGIN TAB METHODS ===
    @FXML
    void handleLogin(ActionEvent event) {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password");
            return;
        }
        
        // Delegate to service layer - get the actual customer object
        currentCustomer = bankingService.getAuthenticatedCustomer(username, password);
        
        if (currentCustomer != null) {
            showSuccessAlert("Login successful! Welcome " + currentCustomer.getFullName());
            clearLoginFields();
            
            // Update UI based on logged-in customer
            updateUIForLoggedInCustomer();
            
            // Navigate to Transaction tab after successful login
            if (mainTabPane != null && transactionTab != null) {
                mainTabPane.getSelectionModel().select(transactionTab);
            }
        } else {
            showAlert("Error", "Invalid username or password");
            loginPasswordField.clear();
        }
    }
    
    // Add a logout method or handle tab changes if needed
    @FXML
    void handleLogout() {
        currentCustomer = null;
        if (mainTabPane != null && loginTab != null) {
            mainTabPane.getSelectionModel().select(loginTab);
        }
        showSuccessAlert("Logged out successfully");
    }
    
    private void updateUIForLoggedInCustomer() {
        // You can customize the UI based on the logged-in customer
        System.out.println("Customer logged in: " + currentCustomer.getFullName());
        
        // For example, filter accounts for this customer
        refreshAccountsTable();
        refreshAccountComboBox();
        refreshTransactionHistory(); // Refresh transaction history when user logs in
    }

    // === TRANSACTION HISTORY METHODS ===
    private void refreshTransactionHistory() {
        if (transactionHistoryData != null) {
            transactionHistoryData.clear();
            for (Account account : bankingService.getAllAccounts()) {
                List<Transaction> transactions = bankingService.getTransactionHistory(account.getAccountNumber());
                for (Transaction transaction : transactions) {
                    String transactionInfo = String.format("Acc: %s | Type: %s | Amount: $%.2f | Date: %s | ID: %s",
                        transaction.getAccountNumber(),
                        transaction.getTransactionType(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getTransactionId());
                    transactionHistoryData.add(transactionInfo);
                }
            }
        }
    }

    // === UTILITY METHODS ===
    private void refreshAccountsTable() {
        if (accountsData != null) {
            accountsData.clear();
            for (Account account : bankingService.getAllAccounts()) {
                Customer customer = account.getCustomer();
                String customerName = (customer != null) ? customer.getFullName() : "Unknown";
                
                String accountInfo = String.format("Acc: %s | Customer: %s | Type: %s | Balance: $%.2f",
                    account.getAccountNumber(), customerName, account.getAccountType(), account.getBalance());
                accountsData.add(accountInfo);
            }
        }
    }
    
    private void refreshAccountComboBox() {
        if (accountComboBox != null) {
            accountComboBox.getItems().clear();
            for (Account account : bankingService.getAllAccounts()) {
                accountComboBox.getItems().add(account.getAccountNumber());
            }
            
            // Update balance display if an account is selected
            if (!accountComboBox.getItems().isEmpty()) {
                accountComboBox.getSelectionModel().selectFirst();
                updateBalanceDisplay(accountComboBox.getValue());
            }
        }
    }
    
    private void refreshCustomersTable() {
        if (customersData != null) {
            customersData.clear();
            for (Customer customer : bankingService.getAllCustomers()) {
                String customerInfo = String.format("ID: %s | Name: %s | User: %s | Type: %s | Accounts: %d",
                    customer.getCustomerId(), customer.getFullName(), customer.getUsername(), 
                    customer.getCustomerType(), customer.getAccounts().size());
                    
                if (customer.getPhone() != null && !customer.getPhone().isEmpty()) {
                    customerInfo += " | Phone: " + customer.getPhone();
                }
                if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
                    customerInfo += " | Email: " + customer.getEmail();
                }
                customersData.add(customerInfo);
            }
        }
    }
    
    private String extractAccountNumber(String accountInfo) {
        // Extract account number from formatted string "Acc: ACC-001 | Customer: John Smith | Type: Savings | Balance: $1000.00"
        if (accountInfo != null && accountInfo.startsWith("Acc: ")) {
            int endIndex = accountInfo.indexOf(" | Customer:");
            if (endIndex != -1) {
                return accountInfo.substring(5, endIndex).trim();
            }
        }
        return null;
    }
    
    // Consistent alert methods
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void clearTransactionFields() {
        amountField.clear();
        if (transactionTypeComboBox != null) {
            transactionTypeComboBox.getSelectionModel().clearSelection();
        }
    }
    
    private void clearAccountFields() {
        accountNumberField.clear();
        customerIdField.clear();
        accountTypeField.clear();
        initialBalanceField.clear();
    }
    
    private void clearCustomerFields() {
        txtCustomerId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtPhone.clear();
        txtEmail.clear();
        if (cmbCustomerType != null) {
            cmbCustomerType.getSelectionModel().clearSelection();
        }
    }
    
    private void clearLoginFields() {
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    @FXML
    void initialize() {
        // Add assertion checks for all FXML injected components
        assert mainTabPane != null : "fx:id=\"mainTabPane\" was not injected: check your FXML file.";
        assert loginTab != null : "fx:id=\"loginTab\" was not injected: check your FXML file.";
        assert customerTab != null : "fx:id=\"customerTab\" was not injected: check your FXML file.";
        assert accountTab != null : "fx:id=\"accountTab\" was not injected: check your FXML file.";
        assert transactionTab != null : "fx:id=\"transactionTab\" was not injected: check your FXML file.";
        assert transactionHistoryTab != null : "fx:id=\"transactionHistoryTab\" was not injected: check your FXML file.";
        assert transactionHistoryTable != null : "fx:id=\"transactionHistoryTable\" was not injected: check your FXML file.";
        assert closeHistoryButton != null : "fx:id=\"closeHistoryButton\" was not injected: check your FXML file.";
        assert accountComboBox != null : "fx:id=\"accountComboBox\" was not injected: check your FXML file.";
        assert transactionTypeComboBox != null : "fx:id=\"transactionTypeComboBox\" was not injected: check your FXML file.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file.";
        assert balanceField != null : "fx:id=\"balanceField\" was not injected: check your FXML file.";
        assert cancelTransactionButton != null : "fx:id=\"cancelTransactionButton\" was not injected: check your FXML file.";
        assert continueTransactionButton != null : "fx:id=\"continueTransactionButton\" was not injected: check your FXML file.";
        assert accountNumberField != null : "fx:id=\"accountNumberField\" was not injected: check your FXML file.";
        assert customerIdField != null : "fx:id=\"customerIdField\" was not injected: check your FXML file.";
        assert accountTypeField != null : "fx:id=\"accountTypeField\" was not injected: check your FXML file.";
        assert initialBalanceField != null : "fx:id=\"initialBalanceField\" was not injected: check your FXML file.";
        assert accountsTableView != null : "fx:id=\"accountsTableView\" was not injected: check your FXML file.";
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file.";
        assert deleteAccountButton != null : "fx:id=\"deleteAccountButton\" was not injected: check your FXML file.";
        assert txtCustomerId != null : "fx:id=\"txtCustomerId\" was not injected: check your FXML file.";
        assert txtFirstName != null : "fx:id=\"txtFirstName\" was not injected: check your FXML file.";
        assert txtLastName != null : "fx:id=\"txtLastName\" was not injected: check your FXML file.";
        assert txtUserName != null : "fx:id=\"txtUserName\" was not injected: check your FXML file.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file.";
        assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file.";
        assert cmbCustomerType != null : "fx:id=\"cmbCustomerType\" was not injected: check your FXML file.";
        assert customersTableView != null : "fx:id=\"customersTableView\" was not injected: check your FXML file.";
        assert registerCustomerButton != null : "fx:id=\"registerCustomerButton\" was not injected: check your FXML file.";
        assert loginUsernameField != null : "fx:id=\"loginUsernameField\" was not injected: check your FXML file.";
        assert loginPasswordField != null : "fx:id=\"loginPasswordField\" was not injected: check your FXML file.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file.";

        System.out.println("Banking Controller initialized!");
        
        // Initialize data lists
        accountsData = FXCollections.observableArrayList();
        customersData = FXCollections.observableArrayList();
        transactionHistoryData = FXCollections.observableArrayList();
        
        // Set up table data
        if (accountsTableView != null) accountsTableView.setItems(accountsData);
        if (customersTableView != null) customersTableView.setItems(customersData);
        if (transactionHistoryTable != null) transactionHistoryTable.setItems(transactionHistoryData);
        
        // Initialize combo boxes
        if (transactionTypeComboBox != null) transactionTypeComboBox.getItems().addAll("Deposit", "Withdrawal");
        if (cmbCustomerType != null) cmbCustomerType.getItems().addAll("Personal", "Business");
        
        // Refresh all data
        refreshAccountsTable();
        refreshCustomersTable();
        refreshAccountComboBox();
        refreshTransactionHistory(); // Add this line
        
        // Set initial balance display (will be empty if no accounts exist)
        if (balanceField != null && accountComboBox != null && !accountComboBox.getItems().isEmpty()) {
            accountComboBox.getSelectionModel().selectFirst();
            updateBalanceDisplay(accountComboBox.getValue());
        }
        
        // Add listener to handle tab changes (optional - for security)
        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                if (newTab == loginTab && currentCustomer != null) {
                    // If user is logged in and goes to login tab, show welcome message
                    System.out.println("Welcome back " + currentCustomer.getFullName());
                } else if (newTab == transactionHistoryTab) {
                    // Refresh transaction history when user navigates to the history tab
                    refreshTransactionHistory();
                }
            });
        }
    }
}