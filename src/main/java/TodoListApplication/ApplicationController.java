package TodoListApplication;
/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    //private int modeList, modeTask; //0 = normal, 1 = edit, 2 = delete
    //private ArrayList<Task> list;
    private double totalCompTasks = 0.0; //keep track of progress of how many completed tasks in a list

    // GUI Fields
    @FXML private TextField nameField;
    @FXML private DatePicker dateField;
    @FXML private Button newButton;
    @FXML private Label statusTxt;
    @FXML private ProgressBar statBar;

    @FXML private TableView<Task> tableView;
    @FXML private TableColumn<Task, String> nameColumn;
    @FXML private TableColumn<Task, String> dateColumn;
    @FXML private TableColumn<Task, String> statusCol;

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
        try {
            Task newTask = new Task(nameField.getText(), dateField.getValue().toString(), false);
            tableView.getItems().add(newTask);
            updateProgressBar();
            statusTxt.setText(":D");
        }catch (Exception e){
            statusTxt.setText("INVALID DATE: MUST BE M/D/YYYY");
        }

        //Get all the items from the table as a list, then add the new person to the list

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
    private void clearAll(ActionEvent event) { //:((((
        ObservableList<Task> allTasks;
        allTasks = tableView.getItems();

        //launch confirmation popup

        for(Task task: allTasks)
            allTasks.remove(task);

        totalCompTasks = 0;
        updateProgressBar();

    }


    public void changeName(TableColumn.CellEditEvent cell)
    {
        Task personSelected =  tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(cell.getNewValue().toString());
    }

    public void changeDate(TableColumn.CellEditEvent cell)
    {
       // ObservableList<Task> selectedRows, allTasks;
        //allTasks = tableView.getItems();

        Task taskSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            taskSelected.setDate(cell.getNewValue().toString());
          statusTxt.setText(":D");
        }catch(Exception e){
            tableView.getItems().add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            tableView.getItems().remove(taskSelected);
          //  allTasks.remove(taskSelected);

            statusTxt.setText("INVALID DATE: MUST BE YYYY-MM-DD or M/D/YYYY");
        }
    }



    public void changeStatus()
    {
        // ObservableList<Task> selectedRows, allTasks;
        //allTasks = tableView.getItems();

        try {
            Task taskSelected = tableView.getSelectionModel().getSelectedItem();

            if (taskSelected.getStatusBool()) {
                taskSelected.setStatus(false);
                totalCompTasks--;
            }else {
                taskSelected.setStatus(true);
                totalCompTasks++;
            }

            updateProgressBar();

            tableView.getItems().add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            tableView.getItems().remove(taskSelected);
          //    allTasks.remove(taskSelected);
            statusTxt.setText("woo");
        }catch (Exception e){
            statusTxt.setText("no task selected");
        }



    }

    public void updateProgressBar(){
        statBar.setProgress(totalCompTasks/tableView.getItems().size());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));

        //Name and date fields editable
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    public void deleteTask()
    {

        ObservableList<Task> allTasks;
        allTasks = tableView.getItems();

        //launch confirmation popup

        Task taskSelected = tableView.getSelectionModel().getSelectedItem();

        if (taskSelected.getStatusBool())
            totalCompTasks--;

        allTasks.remove(taskSelected);

        updateProgressBar();

    }

}
