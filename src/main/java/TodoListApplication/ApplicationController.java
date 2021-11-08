package TodoListApplication;
/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApplicationController {
    private final ObservableList<Task> list = FXCollections.observableArrayList(); //Holds list of user's Tasks
    private double totalCompTasks = 0.0; //Keeps track of progress of how many completed tasks in a list

    // GUI Fields
    @FXML private TableView<Task> tableView;
    @FXML private TableColumn<Task, String> nameColumn;
    @FXML private TableColumn<Task, String> dateColumn;
    @FXML private TableColumn<Task, String> statusColumn;

    @FXML private TextField nameField;
    @FXML private DatePicker dateField;
    @FXML private Label statusMessageTxt;
    @FXML private ProgressBar progressBar;

    //Create new task with information given and add to table
    //throw error if no name given
    //throw error if date invalid
    @FXML
    private void newTask() {

        try {
            //if name field is not black
            if(!nameField.getText().equals("") && !nameField.getText().equals(" ")) {

                //Create new task with or without date
                Task newTask;
                if (dateField.getValue() == null)
                    newTask = new Task(nameField.getText(), false);
                else
                    newTask = new Task(nameField.getText(), dateField.getValue().toString(), false);

                //Add task to table
                list.add(newTask);
                tableView.setItems(list);

                //Update status
                updateProgressBar();
                statusMessageTxt.setText(":D");

                //Clear text fiends
                nameField.setText("");
                dateField.getEditor().clear();
                dateField.setValue(null);

            }else{ //ERROR - name field empty
                statusMessageTxt.setText("name cannot be empty :(");
            }
        }catch (Exception e){ //ERROR - date field invalid
            statusMessageTxt.setText("INVALID DATE - MUST BE M/D/YYYY OR BLANK");
        }

    }

    //Opens window for finding a file and imports list data into chart
    //throw error if file invalid
    @FXML
    private void openList() {

        //Open window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        //Import File Data
        try(Scanner reader = new Scanner(selectedFile)){
            ObservableList<Task> newList = FXCollections.observableArrayList();

            totalCompTasks = Double.parseDouble(reader.nextLine());
            while (reader.hasNextLine()) {
                String name = reader.nextLine();
                String date = reader.nextLine();
                String status = reader.nextLine();
                newList.add(new Task(name, date, status));
            }
            //Update Chart and Status
            list.clear();
            list.addAll(newList);
            tableView.setItems(list);
            updateProgressBar();
            statusMessageTxt.setText("Opened " + selectedFile);

        } catch (Exception e) { //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE - Must be in proper format");
        }


    }

    //opens download pop up and exports data as a txt file
    //throws error if file invalid
    @FXML
    private void downloadList() {

        //Opens window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("myList.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(stage);

        //Write data to txt file
        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write(totalCompTasks + "\n");
            for (Task task : list) {
                writer.write(task.getName() + "\n");
                writer.write(task.getDate() + "\n");
                writer.write(task.getStatus() + "\n");
            }
            statusMessageTxt.setText("Downloaded " + selectedFile);

        }catch (IOException e){ //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE");
        }
    }

    //repopulates chart with all data
    @FXML
    private void showAll() {
        tableView.setItems(list);
    }

    //repopulates the chart with completed data/tasks with status "true"
    @FXML
    private void showCompleted() {
        ObservableList<Task> completed = FXCollections.observableArrayList();

        for(Task task : list){
            if(task.getStatusBool())
                completed.add(task);
        }
        tableView.setItems(completed);
    }

    //repopulates the chart with uncompleted data/tasks with status "false"
    @FXML
    private void showUncompleted() {
        ObservableList<Task> uncompleted = FXCollections.observableArrayList();

        for(Task task : list){
            if(!task.getStatusBool())
                uncompleted.add(task);
        }
        tableView.setItems(uncompleted);
    }

    //Deletes a task from the chart
    //throws error if no task selected
    @FXML
    private void deleteTask(){
        try {
            //Get task
            Task taskSelected = tableView.getSelectionModel().getSelectedItem();

            //Update progress bar counter
            if (taskSelected.getStatusBool())
                totalCompTasks--;

            //Remove task
            list.remove(taskSelected);
            tableView.setItems(list);

            //Update status
            updateProgressBar();
            statusMessageTxt.setText("B-) Deleted " + taskSelected.getName());

        }catch (Exception e){ //ERROR
            statusMessageTxt.setText("no task selected");
        }
    }

    //Deletes all tasks from chart
    //Resets progress bar
    @FXML
    private void clearAll() {
        ObservableList<Task> allTasks = tableView.getItems();
        allTasks.clear();
        list.clear();
        totalCompTasks = 0;
        updateProgressBar();
        statusMessageTxt.setText("all tasks deleted");
    }

    //Changes the name of a task in the chart
    //throws error if name is blank
    @FXML
    private void changeName(CellEditEvent<Task, String> cell) {
        Task taskSelected =  tableView.getSelectionModel().getSelectedItem();

        //Check to see if blank, change the name if not
        if(!cell.getNewValue().equals("") && !cell.getNewValue().equals(" ")) {
            taskSelected.setName(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }else{ //ERROR
            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);
            statusMessageTxt.setText("name cannot be empty :(");
        }
    }

    //Changes the date of a task in the chart
    //throws error if date is invalid
    @FXML
    private void changeDate(CellEditEvent<Task, String> cell) {
       Task taskSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            taskSelected.setDate(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch(Exception e){ //ERROR
            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);
            statusMessageTxt.setText("INVALID DATE - MUST BE YYYY-MM-DD or BLANK");
        }
    }

    //Updates the status of a task (completed or not completed)
    //throws error if no task selected
    @FXML
    private void changeStatus(){
        try {
            Task taskSelected = tableView.getSelectionModel().getSelectedItem();

            //Update progress bar
            if (taskSelected.getStatusBool()) {
                taskSelected.setStatus(false);
                totalCompTasks--;
            }else {
                taskSelected.setStatus(true);
                totalCompTasks++;
            }
            updateProgressBar();

            //Update chart
            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);

            //Status messages
            statusMessageTxt.setText("woo");
            if(totalCompTasks == list.size())
                statusMessageTxt.setText("ALL TASKS COMPLETE - CONGRATS KING B-)");

        }catch (Exception e){ //ERROR
            statusMessageTxt.setText("no task selected");
        }
    }

    //Update the progress bar when changes are made
    private void updateProgressBar(){
        progressBar.setProgress(totalCompTasks/tableView.getItems().size());
    }

    //Initial construction of the table
    public void initialize() {
        //set up the columns in the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        //Name and date fields editable
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
