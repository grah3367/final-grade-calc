package mainApp.views;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.model.Assignment;
import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 * Created by Dan.
 */
public class AssignmentEditController {

    @FXML private TextField assignmentTitleField;
    @FXML private TextField assignmentPointsField;
    @FXML private TextField assignmentPossPointsField;
    @FXML private Label editTitle;

    public void setEditTitle(String title) {
        editTitle.setText(title);
    }


    private Stage dialogStage;
    private Assignment assignment;
    private boolean OkClicked = false;

    @FXML private void initialize(){

    }

    public  void setAssignment(Assignment assignment){
        this.assignment = assignment;

        // set the fields
        assignmentTitleField.setText(assignment.getTitle());
        assignmentPointsField.setText(Double.toString(assignment.getPoints()));
        assignmentPossPointsField.setText(Double.toString(assignment.getPossPoints()));
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked(){
        return OkClicked;
    }

    public void handleOk(){

        if(isValidInput()){
            assignment.setTitle(assignmentTitleField.getText());
            double possPoints = Double.parseDouble(assignmentPossPointsField.getText());
            double points = Double.parseDouble(assignmentPointsField.getText());
            assignment.setPoints((int) points);
            assignment.setPossPoints((int) possPoints);
            double percent = assignment.calcPercent(points, possPoints);
            assignment.setPercent(percent);
            assignment.setGrade(assignment.percentToLetter(percent));
            OkClicked=true; dialogStage.close();
        }

    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public boolean isValidInput(){
        String errorMessage = "";

        if (assignmentTitleField.getText() == null || assignmentTitleField.getText().length() == 0) {
            errorMessage += "Please enter a valid value for title!\n";
        }
        if ((assignmentPointsField.getText() == null || assignmentPointsField.getText().length() == 0) && isNumber(assignmentPointsField.getText())) {
            errorMessage += "Please enter a valid value for points!\n";
        }
        if ((assignmentPossPointsField.getText() == null || assignmentPossPointsField.getText().length() == 0) && isNumber(assignmentPossPointsField.getText())) {
            errorMessage += "Please enter a valid value for possible points!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
