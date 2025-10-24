import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class CustomerController {
    
    @FXML private TextField txtCustomerId;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private TableView<String> customersTableView; 
    
    private ObservableList<String> customersData;
    
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
        
        // Input validation
        if (customerId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || 
            username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill required fields (Customer ID, Name, Username, Password)");
            return;
        }
        
        // customer registration logic
        String customerInfo = registerCustomer(customerId, firstName, lastName, username, password, phone, email, customerType);
        
        if (customerInfo != null) {
            if (customersData != null) {
                customersData.add(customerInfo);
                clearCustomerFields();
                showAlert("Success", "Customer registered successfully: " + customerInfo);
            } else {
                showAlert("Error", "Failed to register customer - data not initialized");
            }
        } else {
            showAlert("Error", "Failed to register customer");
        }
    }
    
    // customer registration logic
    private String registerCustomer(String customerId, String firstName, String lastName, 
                                  String username, String password, String phone, 
                                  String email, String customerType) {
        // validation
        if (customerId.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            return null;
        }
        
        // Create customer info string
        String customerInfo = "ID: " + customerId + " | Name: " + firstName + " " + lastName + 
                            " | User: " + username + " | Type: " + customerType;
        
        if (phone != null && !phone.isEmpty()) {
            customerInfo += " | Phone: " + phone;
        }
        if (email != null && !email.isEmpty()) {
            customerInfo += " | Email: " + email;
        }
        
        System.out.println("Registering customer: " + customerInfo);
        return customerInfo;
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
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void initialize() {
        System.out.println("Customer Controller initialized");
        
        // Initialize customers data
        customersData = FXCollections.observableArrayList();
        if (customersTableView != null) {
            customersTableView.setItems(customersData);
        }
        
        // Initialize combo box
        if (cmbCustomerType != null) {
            cmbCustomerType.getItems().addAll("Personal", "Business", "Student");
        }
        
        // sample customers for testing
        customersData.add("ID: CUST001 | Name: John Smith | User: john.smith | Type: Personal | Phone: 555-0101 | Email: john@email.com");
        customersData.add("ID: CUST002 | Name: Jane Doe | User: jane.doe | Type: Business | Phone: 555-0102 | Email: jane@company.com");
    }
}