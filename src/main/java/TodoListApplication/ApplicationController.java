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

    //add new task with information given
    //throw error if no name given
    //throw error if date invalid
    @FXML
    private void newTask() {

        try {
            if(!nameField.getText().equals("") && !nameField.getText().equals(" ")) {

                Task newTask;
                if (dateField.getValue() == null)
                    newTask = new Task(nameField.getText(), false);
                else
                    newTask = new Task(nameField.getText(), dateField.getValue().toString(), false);

                list.add(newTask);
                tableView.setItems(list);

                updateProgressBar();
                statusMessageTxt.setText(":D");
            }else{
                statusMessageTxt.setText("name cannot be empty :(");
            }
        }catch (Exception e){
            statusMessageTxt.setText("INVALID DATE - MUST BE M/D/YYYY OR BLANK");
        }

    }

    //opens window for finding a file and adds that list data to lists
    //updates status bar to an error message if file invalid
    @FXML
    private void openList() {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        try(Scanner reader = new Scanner(selectedFile)){
            list.clear();
            totalCompTasks = Double.parseDouble(reader.nextLine());

            while (reader.hasNextLine()) {
                String name = reader.nextLine();
                String date = reader.nextLine();
                String status = reader.nextLine();
                list.add(new Task(name, date, status));
            }

            tableView.setItems(list);
            updateProgressBar();
            statusMessageTxt.setText("Opened " + selectedFile);
        } catch (Exception e) {
            statusMessageTxt.setText("INVALID FILE - Must be in proper format");
        }


    }

    //opens download pop up and then downloads file
    @FXML
    private void downloadList() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("myList.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(stage);

        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write(totalCompTasks + "\n");
            for (Task task : list) {
                writer.write(task.getName() + "\n");
                writer.write(task.getDate() + "\n");
                writer.write(task.getStatus() + "\n");
            }
            statusMessageTxt.setText("Downloaded " + selectedFile);
        }catch (IOException e){
            statusMessageTxt.setText("INVALID FILE");
        }
    }

    //repopulates the chart with all data
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

    @FXML
    private void deleteTask(){
        try {
            Task taskSelected = tableView.getSelectionModel().getSelectedItem();

            if (taskSelected.getStatusBool())
                totalCompTasks--;

            list.remove(taskSelected);
            tableView.setItems(list);

            updateProgressBar();
            statusMessageTxt.setText("B-) Deleted " + taskSelected.getName());
        }catch (Exception e){
            statusMessageTxt.setText("no task selected");
        }

    }

    //opens confirmation popup, then deletes all tasks
    @FXML
    private void clearAll() {
        ObservableList<Task> allTasks = tableView.getItems();
        allTasks.clear();
        list.clear();
        totalCompTasks = 0;
        updateProgressBar();
        statusMessageTxt.setText("all tasks deleted");
    }

    @FXML
    private void changeName(CellEditEvent<Task, String> cell) {
        Task taskSelected =  tableView.getSelectionModel().getSelectedItem();
        if(!cell.getNewValue().equals("") && !cell.getNewValue().equals(" ")) {
            taskSelected.setName(cell.getNewValue());
            statusMessageTxt.setText(":D");
        }else{
            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);
            statusMessageTxt.setText("name cannot be empty :(");
        }
    }

    @FXML
    private void changeDate(CellEditEvent<Task, String> cell) {
       Task taskSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            taskSelected.setDate(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch(Exception e){
            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);
            statusMessageTxt.setText("INVALID DATE - MUST BE YYYY-MM-DD or BLANK");
        }
    }


    @FXML
    private void changeStatus(){
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

            list.add(new Task(taskSelected.getName(), taskSelected.getDate(), taskSelected.getStatusBool()));
            list.remove(taskSelected);
            tableView.setItems(list);

            statusMessageTxt.setText("woo");

            if(totalCompTasks == list.size())
                statusMessageTxt.setText("ALL TASKS COMPLETE - CONGRATS KING B-)");

        }catch (Exception e){
            statusMessageTxt.setText("no task selected");
        }
    }

    private void updateProgressBar(){
        progressBar.setProgress(totalCompTasks/tableView.getItems().size());
    }

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
