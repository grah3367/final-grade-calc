package mainApp.model;

import javafx.beans.property.*;

/**
 * Created by Dan on 5/5/2016.
 */
public class Assignment {

    StringProperty title, grade;
    IntegerProperty points, possPoints;
    DoubleProperty percent;

    //default constructor
    public Assignment() {

    }

    // constructor with data
    public Assignment(String title, int points, int possPoints) {
        this.title = new SimpleStringProperty(title);
        this.percent = new SimpleDoubleProperty(calcPercent(points, possPoints));
        this.grade = new SimpleStringProperty(percentToLetter(calcPercent(points, possPoints)));
        this.points = new SimpleIntegerProperty(points);
        this.possPoints = new SimpleIntegerProperty(possPoints);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public double getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public double getPossPoints() {
        return possPoints.get();
    }

    public IntegerProperty possPointsProperty() {
        return possPoints;
    }

    public double getPercent() {
        return percent.get();
    }

    public DoubleProperty percentProperty() {
        return percent;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public void setPossPoints(int possPoints) {
        this.possPoints.set(possPoints);
    }

    public void setPercent(double percent) {
        this.percent.set(percent);
    }

    // helper functions
    public static double calcPercent(double earnedPoints, double totalPoints){
        return (earnedPoints/totalPoints)*100;
    }
    public static String percentToLetter(double percent){
        String gradeLetter = "";

        if(percent >= 90)
            gradeLetter = "A";
        if(percent >= 80 && percent <= 89.9 )
            gradeLetter = "B";
        if(percent >= 70 && percent <= 79.9)
            gradeLetter = "C";
        if(percent >= 60 && percent <= 69.9)
            gradeLetter = "D";
        if(percent <= 59.9)
            gradeLetter = "F";

        return gradeLetter;
    }

}
