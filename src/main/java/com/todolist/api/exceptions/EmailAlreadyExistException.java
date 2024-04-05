package com.todolist.api.exceptions;

public class EmailAlreadyExistException  extends RuntimeException{

    public EmailAlreadyExistException() {
        super("E-mail already registered");
    }
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
