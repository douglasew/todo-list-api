package com.todolist.api.exceptions;

public class InvalidDateRangeException extends RuntimeException{

    public InvalidDateRangeException() {
        super("The end date must be greater than the start date");
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
