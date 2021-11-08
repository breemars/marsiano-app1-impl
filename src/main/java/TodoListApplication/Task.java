package TodoListApplication;
/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private SimpleStringProperty name;
    private LocalDate date;
    private String status;
    private boolean statBool;

    //Constructor - no date given
    public Task(String name, boolean statBool){
        setName(name);
        setStatus(statBool);
    }

    //Constructor - date is given
    public Task(String name, String date, boolean statBool){
        setName(name);
        setDate(date);
        setStatus(statBool);
    }

    //Constructor used when importing data from file
    public Task(String name, String date, String status){
        setName(name);
        setDate(date);
        this.status = status;
        statBool = status.equals("O");
    }

    //Update name of task
    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }

    //Update date, formatted as YYYY-MM-DD
    //Date can be blank, but not invalid
    public void setDate(String date){
        if(!date.equals("") && !date.equals(" ")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.date = LocalDate.parse(date, formatter);
        }else{
            this.date = null;
        }
    }

    //Updates status of task
    public void setStatus(boolean statBool){
        this.statBool = statBool;
        if(statBool) {
            status = "O";
        }else{
            status = "X";
        }
    }

    //gets the task name
    public String getName(){
        return name.get();
    }

    //gets the task date, formatted as YYYY-MM-DD
    //returns blank if date not given
    public String getDate(){
        if(date == null)
            return "";
        return date.toString();
    }

    //gets the task status in string format
    //X for incomplete, O for complete
    public String getStatus(){
        return status;
    }

    //gets the task status boolean
    public boolean getStatusBool(){
        return statBool;
    }

}
