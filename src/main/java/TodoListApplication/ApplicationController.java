package TodoListApplication;
/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ApplicationController {
    private int modeList, modeTask; //0 = normal, 1 = edit, 2 = delete

    // Where ill define my GUI controls defined in FXML and used by the controller's code
    //@FXML
    //private type name;

    //Mark completed/uncompleted (& update progress bar)
    //If edit mode on - populate fields with info
    //If delete mode on - open pop up and ask for confirmation
    @FXML
    private void taskSelected(ActionEvent event) {

    }

    //add new task with information given
    //throw error if no name given
    //throw error if date invalid
    @FXML
    private void newTask(ActionEvent event) {

    }

    //replace old task information with new entered
    @FXML
    private void editTask(ActionEvent event) {

    }

    //opens window for finding a file and adds that list data to lists
    //updates status bar to an error message if file invalid
    @FXML
    private void openList(ActionEvent event) {}

    //opens download pop up and then downloads file
    @FXML
    private void downloadList(ActionEvent event) {}

    //repopulates the chart with all data
    @FXML
    private void showAll(ActionEvent event) {}

    //repopulates the chart with completed data/tasks with status "true"
    @FXML
    private void showCompleted(ActionEvent event) {}

    //repopulates the chart with uncompleted data/tasks with status "false"
    @FXML
    private void showUncompleted(ActionEvent event) {}

    //opens confirmation popup, then deletes all tasks
    @FXML
    private void clearAll(ActionEvent event) {}

    //updates status bar label
    @FXML
    private void deleteTaskMode(ActionEvent event) {}

    //updates status bar label
    @FXML
    private void editTaskMode(ActionEvent event) {}

}
