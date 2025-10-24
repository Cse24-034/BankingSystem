import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class AccountController {
    
    @FXML private TextField accountNumberField;
    @FXML private TextField customerIdField;
    @FXML private TextField accountTypeField;
    @FXML private TextField initialBalanceField;
    @FXML private TableView<String> accountsTableView; // Changed to String for simplicity
    
    private ObservableList<String> accountsData;
    
    @FXML
    void handleCreateAccount(ActionEvent event) {
        String accountNumber = accountNumberField.getText();
        String customerId = customerIdField.getText();
        String accountType = accountTypeField.getText();
        String initialBalanceStr = initialBalanceField.getText();
        
        // Input validation
        if (accountNumber.isEmpty() || customerId.isEmpty() || accountType.isEmpty() || initialBalanceStr.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }
        
        try {
            double initialBalance = Double.parseDouble(initialBalanceStr);
            
            // Simple account creation logic
            String accountInfo = "Acc: " + accountNumber + " | Cust: " + customerId + " | Type: " + accountType + " | Balance: $" + initialBalance;
            
            if (accountsData != null) {
                accountsData.add(accountInfo);
                clearAccountFields();
                showAlert("Success", "Account created successfully: " + accountInfo);
            } else {
                showAlert("Error", "Failed to create account - data not initialized");
            }
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Initial balance must be a valid number");
        }
    }
    
    @FXML
    void handleDeleteAccount(ActionEvent event) {
        String selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedAccount != null) {
            if (accountsData != null) {
                accountsData.remove(selectedAccount);
                showAlert("Success", "Account deleted successfully");
            } else {
                showAlert("Error", "Failed to delete account");
            }
        } else {
            showAlert("Error", "Please select an account to delete");
        }
    }
    
    private void clearAccountFields() {
        accountNumberField.clear();
        customerIdField.clear();
        accountTypeField.clear();
        initialBalanceField.clear();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void initialize() {
        System.out.println("Account Controller initialized");
        
        // Initialize the accounts data
        accountsData = FXCollections.observableArrayList();
        if (accountsTableView != null) {
            accountsTableView.setItems(accountsData);
        }
        
        // Add some sample accounts for testing
        accountsData.add("Acc: 001 | Cust: CUST001 | Type: Savings | Balance: $1000.00");
        accountsData.add("Acc: 002 | Cust: CUST002 | Type: Checking | Balance: $2500.00");
    }
}