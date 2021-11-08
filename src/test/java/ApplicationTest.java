/*
 *  UCF COP3330 Fall 2021 Application.Application Assignment 1 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import TodoListApplication.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ApplicationTest {
    private Task test2;

    @Test
    void createTaskNoDate(){
        Task test1 = new Task("test", false);
        assertEquals("test", test1.getName());
        assertFalse(test1.getStatusBool());
        assertEquals("X", test1.getStatus());
    }

    @BeforeEach
    @Test
    void createTaskWithBool(){
        test2 = new Task("test", "2021-07-11", false);
        assertEquals("test", test2.getName());
        assertEquals("2021-07-11", test2.getDate());
        assertFalse(test2.getStatusBool());
        assertEquals("X", test2.getStatus());
    }

    @Test
    void createTaskWithString(){
        Task test3 = new Task("test", "2020-10-10", "O");
        assertEquals("test", test3.getName());
        assertEquals("2020-10-10", test3.getDate());
        assertEquals("O", test3.getStatus());
        assertTrue(test3.getStatusBool());
    }

    @Test
    void changeNameOfTask(){
        test2.setName("newName");
        assertEquals("newName", test2.getName());
    }

    @Test
    void changeDateOfTask(){
        test2.setDate("2019-02-02");
        assertEquals("2019-02-02", test2.getDate());
    }

    @Test
    //Incorrect format so date does not change
    void changeDateOfTaskWithIncorrectDate(){
        try{
            test2.setDate("20-02-2020");
        }catch(Exception e){
            assertEquals("2021-07-11", test2.getDate());
        }
    }

    @Test
    void markTaskComplete(){
        test2.setStatus(true);
        assertTrue(test2.getStatusBool());
        assertEquals("O", test2.getStatus());
    }

    @Test
    void markTaskIncomplete(){
        test2.setStatus(false);
        assertFalse(test2.getStatusBool());
        assertEquals("X", test2.getStatus());
    }

}
