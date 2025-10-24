import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class TransactionController {
    
    @FXML private ComboBox<String> accountComboBox;
    @FXML private ComboBox<String> transactionTypeComboBox;
    @FXML private TextField amountField;
    @FXML private TextField balanceField;
    @FXML private TableView<String> transactionHistoryTable; 
    
    private ObservableList<String> transactionHistoryData;
    
    @FXML
    void handleContinueTransaction(ActionEvent event) {
        String accountNumber = accountComboBox.getValue();
        String transactionType = transactionTypeComboBox.getValue();
        String amountStr = amountField.getText();
        
        // Input validation
        if (accountNumber == null || transactionType == null || amountStr.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountStr);
            
            // Simple transaction processing logic
            boolean success = processTransaction(accountNumber, transactionType, amount);
            
            if (success) {
                // Add to transaction history
                String transactionRecord = accountNumber + " - " + transactionType + " -$" + amount;
                if (transactionHistoryData != null) {
                    transactionHistoryData.add(transactionRecord);
                }
                
                updateBalanceDisplay(accountNumber, transactionType, amount);
                clearTransactionFields();
                showAlert("Success", "Transaction completed successfully");
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
    
    // Simple transaction processing logic
    private boolean processTransaction(String accountNumber, String transactionType, double amount) {
        // Basic validation
        if (amount <= 0) {
            return false;
        }
        
        // For withdrawals, check if sufficient funds (simplified logic)
        if ("Withdrawal".equals(transactionType)) {
            double currentBalance = getCurrentBalance(accountNumber);
            if (amount > currentBalance) {
                return false; // Insufficient funds
            }
        }
        
        System.out.println("Processing " + transactionType + " of $" + amount + " for account: " + accountNumber);
        return true;
    }
    
    // Simple balance calculation
    private double getCurrentBalance(String accountNumber) {
        // Default balance for demo
        return 500.00;
    }
    
    private void updateBalanceDisplay(String accountNumber, String transactionType, double amount) {
        double currentBalance = getCurrentBalance(accountNumber);
        double newBalance = currentBalance;
        
        if ("Deposit".equals(transactionType)) {
            newBalance = currentBalance + amount;
        } else if ("Withdrawal".equals(transactionType)) {
            newBalance = currentBalance - amount;
        }
        
        balanceField.setText(String.format("$%.2f", newBalance));
    }
    
    private void clearTransactionFields() {
        amountField.clear();
        if (transactionTypeComboBox != null) {
            transactionTypeComboBox.getSelectionModel().clearSelection();
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void initialize() {
        System.out.println("Transaction Controller initialized");
        
        // Initialize transaction history data
        transactionHistoryData = FXCollections.observableArrayList();
        if (transactionHistoryTable != null) {
            transactionHistoryTable.setItems(transactionHistoryData);
        }
        
        // Initialize combo boxes
        if (transactionTypeComboBox != null) {
            transactionTypeComboBox.getItems().addAll("Deposit", "Withdrawal");
        }
        
        if (accountComboBox != null) {
            accountComboBox.getItems().addAll("ACC-001", "ACC-002", "ACC-003");
        }
        
        // Set initial balance
        if (balanceField != null) {
            balanceField.setText("$500.00");
        }
        
        // Add sample transaction history
        transactionHistoryData.add("ACC-001 - Deposit - $500.00");
        transactionHistoryData.add("ACC-002 - Withdrawal -$200.00");
    }
}