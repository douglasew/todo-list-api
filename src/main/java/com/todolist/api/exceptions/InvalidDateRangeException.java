package com.todolist.api.exceptions;

public class InvalidDateRangeException extends RuntimeException{

    public InvalidDateRangeException() {
        super("A data de término deve ser posterior à data atual");
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
