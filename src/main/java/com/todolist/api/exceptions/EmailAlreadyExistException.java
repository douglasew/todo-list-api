package com.todolist.api.exceptions;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException() {
        super("E-mail já cadastrado");
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
