// Main.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load main FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BankingsystemUML.fxml"));
        Parent root = loader.load();
        
        // Get the controller to set initial tab
        BankingController controller = loader.getController();
        controller.initializeLoginTab();
        
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}