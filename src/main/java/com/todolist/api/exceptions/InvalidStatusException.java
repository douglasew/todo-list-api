package com.todolist.api.exceptions;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException() {
        super("Status não encontrado");
    }

    public InvalidStatusException(String message) {
        super(message);
    }
}
