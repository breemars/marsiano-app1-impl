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
    private boolean statBool;
    private String status;

    //Constructor - Sets variables
    public Task(String name, boolean status){}
    public Task(String name, String date, boolean status){
        this.name = new SimpleStringProperty(name);
        setDate(date);
        setStatus(status);
    }

    //updates name
    //MUST NOT BE BLANK
    public void setName(String name){
       // if(name.equals(""))
           // throw Exception;
        this.name = new SimpleStringProperty(name);
    }

    //updates date, formatted as YYYY-MM-DD
    //date can be blank, but not invalid
    public void setDate(String date){
        if(!date.equals("oo")) {
            this.date = validateDate(date);
        }else{
            this.date = null;
        }
    }

    //updates completed or not
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
    public String getDate(){
        return date.toString();
    }

    //gets the task status
    public boolean getStatusBool(){
        return statBool; //✔✓☑ ✗✘ ✖
    }

    public LocalDate validateDate(String date) { //throw an error here to print message
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);

    }
}
