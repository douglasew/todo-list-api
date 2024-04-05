package com.todolist.api.exceptions;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException() {
        super("Username already registered");
    }
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
