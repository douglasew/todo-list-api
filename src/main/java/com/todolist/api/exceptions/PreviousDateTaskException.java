package com.todolist.api.exceptions;

public class PreviousDateTaskException extends RuntimeException{
    public PreviousDateTaskException() {
        super("Enter a date greater than the current date");
    }

    public PreviousDateTaskException(String message) {
        super(message);
    }
}
