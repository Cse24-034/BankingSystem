import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
    
    @FXML private TextField loginUsernameField;
    @FXML private TextField loginPasswordField;
    
    @FXML
    void handleLogin(ActionEvent event) {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        
        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password");
            return;
        }
        
        // Simple authentication logic (replace with your business logic later)
        boolean loginSuccess = authenticateUser(username, password);
        
        if (loginSuccess) {
            showAlert("Success", "Login successful for user: " + username);
            System.out.println("Login successful for user: " + username);
            // Navigate to main application
        } else {
            showAlert("Login Failed", "Invalid username or password");
            loginPasswordField.clear();
        }
    }
    
    // Simple authentication method (replace with BankingSystem later)
    private boolean authenticateUser(String username, String password) {
        // Default credentials for testing
        return "admin".equals(username) && "password".equals(password);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void initialize() {
        System.out.println("Login Controller initialized");
    }
}