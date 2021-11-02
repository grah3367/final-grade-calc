package mainApp.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import mainApp.Main;

/**
 * Created by Dan on 5/6/2016.
 */
public class RootLayoutController {
    // Reference to the main application
    private Main mainApp;


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getAssignmentsList().clear();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dan's Grade App");
        alert.setHeaderText("About");
        alert.setContentText("Author: Daniel Graham");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
