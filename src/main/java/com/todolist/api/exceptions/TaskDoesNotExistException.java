package com.todolist.api.exceptions;

public class TaskDoesNotExistException extends RuntimeException{

    public TaskDoesNotExistException() {
        super("Task does not exist");
    }

    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
