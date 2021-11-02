package mainApp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainApp.model.Assignment;
import mainApp.views.AssignmentEditController;
import mainApp.views.GradeOverviewController;
import mainApp.views.RootLayoutController;

import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;
    private Pane rootLayout;
    private ObservableList<Assignment> assignmentsList = FXCollections.observableArrayList();


    // Constructor for the Main class
    public Main(){

        assignmentsList.add(new Assignment("Assignment 1",30,30));
        assignmentsList.add(new Assignment("Assignment 2",30,30));
        assignmentsList.add(new Assignment("Quiz 1",25,25));
        assignmentsList.add(new Assignment("Assignment 3",30,30));
        assignmentsList.add(new Assignment("Assignment 4",30,30));
        assignmentsList.add(new Assignment("Quiz 2",25,25));
        assignmentsList.add(new Assignment("Assignment 5",30,30));
        assignmentsList.add(new Assignment("Midterm",150,200));
        assignmentsList.add(new Assignment("Assignment 6",30,30));
        assignmentsList.add(new Assignment("Assignment 7",30,30));
        assignmentsList.add(new Assignment("Quiz 3",23,25));
        assignmentsList.add(new Assignment("Assignment 8",30,30));
        assignmentsList.add(new Assignment("Extra Credit",25,0));
        assignmentsList.add(new Assignment("Final Project",200,200));

    }

    @Override
    public void start(Stage primaryStage){

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Daniel Graham's Grade Calc");
        this.primaryStage.setResizable(false);
        initRootLayout();
        showGradeOverView();

    }

    public void initRootLayout(){
        try {
            // load root layout fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/RootLayout.fxml"));

            rootLayout =  loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            // show the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows gradeOverView inside the main root layout.
     */
    public void showGradeOverView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/GradeOverview.fxml"));
            Pane gradeOverview = loader.load();
            rootLayout.getChildren().add(gradeOverview);

            // set this main class
            GradeOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Assignment> getAssignmentsList() {
        return assignmentsList;
    }

    public boolean showAssignmentDialog(Assignment assignment, String title){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/AssignmentEditDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            // Set the assignment
            AssignmentEditController controller = loader.getController();
            controller.setEditTitle(title);
            controller.setDialogStage(dialogStage);
            controller.setAssignment(assignment);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
