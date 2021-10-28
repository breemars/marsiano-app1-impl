package TodoListApplication;
/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class TodoListApplication extends javafx.application.Application {
    private double progress; //keep track of progress of how many completed tasks in a list
    private int totalCompTasks; //total number of completed tasks


    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Application.fxml")));

        Scene scene = new Scene(root); // attach scene graph to scene
        stage.setTitle("TODO LIST"); // displayed in window's title bar
        stage.setScene(scene); // attach scene to stage
        stage.show(); // display the stage

    }

    //Will an array list of Lists as well
    public static void main(String[] args) {
        launch(args);
    }


    //called when user marks off a task/when the status of the task is now true
    void updateProgressBarAdd(){}

    //called when user de-marks off a task/when the status of the task is now false
    void updateProgressBarMinus(){}
}
