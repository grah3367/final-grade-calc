package mainApp.views;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import mainApp.Main;
import mainApp.model.Assignment;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static mainApp.model.Assignment.calcPercent;
import static mainApp.model.Assignment.percentToLetter;

/**
 * Created by Dan.
 */

public class GradeOverviewController implements Initializable {

    private Main mainApp;

    @FXML
    private TableView<Assignment> assignmentTableView;
    @FXML
    private TableColumn<Assignment, String> assignmentTitleTableColumn;
    @FXML
    private TableColumn<Assignment, String> assignmentGradeTableColumn;


    // label
    @FXML private Label assignmentTitle;
    @FXML private Label assignmentPoints;
    @FXML private Label assignmentPointsPoss;
    @FXML private Label assignmentPercentage;
    @FXML private Label assignmentGrade;

    // Buttons
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;


    // total
    @FXML private Label assignmentFinalGrade;
    @FXML private Label assignmenTotalPointsEarned;
    @FXML private Label assignmenTotalPoints;
    @FXML private Label assignmenFinalPercentage;

    @FXML private Pane assignmentDetailsPane;

    public GradeOverviewController() {
    }


    public void initialize(URL location, ResourceBundle resources){
        assignmentTitleTableColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        assignmentGradeTableColumn.setCellValueFactory(cellData -> cellData.getValue().pointsProperty().asObject().asString().concat(
                new String("/")).concat(
                cellData.getValue().possPointsProperty().asObject().asString()) );
        showAssignmentDetails(null);
        assignmentTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAssignmentDetails(newValue)
        );
    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        // set data
        assignmentTableView.setItems(mainApp.getAssignmentsList());
    }

    public void showAssignmentDetails(Assignment assignment){

        if(assignment != null){
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            assignmentDetailsPane.setOpacity(1);
            assignmentTitle.setText(assignment.getTitle());
            assignmentPoints.setText( Double.toString(assignment.getPoints()));
            assignmentPointsPoss.setText( Double.toString(assignment.getPossPoints()));
            assignmentPercentage.setText( Double.toString(calcPercent(assignment.getPoints(),assignment.getPossPoints())));
            assignmentGrade.setText(assignment.getGrade());
        } else {
            // assignment is null so delete all the text
            assignmentTitle.setText("");
            assignmentPoints.setText("");
            assignmentPointsPoss.setText("");
            assignmentPercentage.setText("");
            assignmentGrade.setText("");
            // total

            assignmenFinalPercentage.setText("");
            assignmentFinalGrade.setText("");
            assignmenTotalPointsEarned.setText("");
            assignmenTotalPoints.setText("");

            // set edit and delete to inactive
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            assignmentDetailsPane.setOpacity(.40);

        }
    }

    @FXML private void newAssignmnetHanlder(){
        Assignment newAssign = new Assignment("New",0,0);

        boolean oKClicked = mainApp.showAssignmentDialog(newAssign, "Add new Assignment");
        if(oKClicked){
            mainApp.getAssignmentsList().add(newAssign);
        }
    }

    @FXML private void EditAssignmentHandler(){
        Assignment selectedAssignment = assignmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAssignment != null){
            boolean oKClicked = mainApp.showAssignmentDialog(selectedAssignment, "Editing " + selectedAssignment.getTitle());
            if(oKClicked){
                showAssignmentDetails(selectedAssignment);
            }
        } else {

        }
    }


    public void deleteAssignmentHandler(){
        int selected = assignmentTableView.getSelectionModel().getSelectedIndex();

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Delete Assignment");
        //alert.setHeaderText("Are you sure you want to delete grade?");
        alert.setContentText("Delete " + assignmentTableView.getItems().get(selected).getTitle() + "?");
        ButtonType buttonTypeDel = new ButtonType("Confirm Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeDel, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == buttonTypeDel){
            assignmentTableView.getItems().remove(selected);
        }
    }

    /*
        @FXML private Label assignmentFinalGrade;
    @FXML private Label assignmenTotalPointsEarned;
    @FXML private Label assignmenTotalPoints;
    @FXML private Label assignmenFinalPercentage;


     */

    public void calcFinalGradeHandler(){
        DecimalFormat df = new DecimalFormat("###.##");
        ObservableList<Assignment> assignmentList = mainApp.getAssignmentsList();
        Assignment assign = null;
        double totalPoints=0.0;
        double totalPossPoints=0.0;

        for(Assignment assignment : assignmentList){
            totalPoints += assignment.getPoints();
            totalPossPoints += assignment.getPossPoints();
        }

        assignmenTotalPointsEarned.setText(Double.toString(totalPoints));
        assignmenTotalPoints.setText(Double.toString(totalPossPoints));
        double percent = calcPercent(totalPoints, totalPossPoints);
        assignmenFinalPercentage.setText(df.format(percent));
        String finalGradeLetter = percentToLetter( percent);
        assignmentFinalGrade.setText(finalGradeLetter);

        System.out.println(finalGradeLetter);
        assignmentFinalGrade.getStyleClass().clear();
        assignmentFinalGrade.getStyleClass().add("h1");

        if(finalGradeLetter.equals("A") || finalGradeLetter.equals("B")) {
            assignmentFinalGrade.getStyleClass().add("success");
            System.out.println("is a or b");
        } else if(finalGradeLetter.equals("C") || finalGradeLetter.equals("D")){
            assignmentFinalGrade.getStyleClass().add("warning");
            System.out.println("c or d");
        } else {
            assignmentFinalGrade.getStyleClass().add("fail");
            System.out.println("f");
        }

    }


}
