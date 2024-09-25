package com.todolist.api.exceptions;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException() {
        super("Nome de usuário já cadastrado");
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
