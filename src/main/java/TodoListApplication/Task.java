package TodoListApplication;/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

public class Task {
    private String name;
    private String date;
    private boolean status;

    //Constructor - Sets variables
    Task(String name, boolean status){}
    Task(String name, String date, boolean status){} //date must be valid

    //updates name
    void setName(){}

    //updates date, formatted as YYYY-MM-DD
    //date can be blank, but not invalid
    void setDate(){}

    //updates completed or not
    void setStatus(){}

    //gets the task name
    String getName(){
        return "";
    }

    //gets the task date, formatted as YYYY-MM-DD
    String getDate(){
        return "";
    }

    //gets the task status
    boolean getStatus(){
        return true;
    }
}
