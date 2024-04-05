package com.todolist.api.exceptions;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException() {
        super("User without permission");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
