package com.todolist.api.exceptions;

public class UnauthorizedUserException extends RuntimeException{

    public UnauthorizedUserException() {
        super("Usuário sem permissão");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
