// Main.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load main FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BankingsystemUML.fxml"));
            Parent root = loader.load();
            
            // Get the controller to set initial tab
            BankingController controller = loader.getController();
            if (controller != null) {
                controller.initializeLoginTab();
            }
            
            Scene scene = new Scene(root, 700, 400);
            stage.setTitle("Banking System");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}