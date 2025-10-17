import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PleaseProvideControllerClassName {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> cmbCustomerType;

    @FXML
    private ComboBox<?> cmbCustomerType1;

    @FXML
    private ComboBox<?> cmbCustomerType11;

    @FXML
    private Button myButton;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtLastName1;

    @FXML
    private TextField txtLastName11;

    @FXML
    private TextField txtPhone;

    @FXML
    void handleButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert cmbCustomerType != null : "fx:id=\"cmbCustomerType\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert cmbCustomerType1 != null : "fx:id=\"cmbCustomerType1\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert cmbCustomerType11 != null : "fx:id=\"cmbCustomerType11\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtCustomerId != null : "fx:id=\"txtCustomerId\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtFirstName != null : "fx:id=\"txtFirstName\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtLastName != null : "fx:id=\"txtLastName\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtLastName1 != null : "fx:id=\"txtLastName1\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtLastName11 != null : "fx:id=\"txtLastName11\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";
        assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'BankingsystemUML.fxml'.";

    }

}
