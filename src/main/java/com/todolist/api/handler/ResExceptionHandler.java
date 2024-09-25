package com.todolist.api.handler;

import com.todolist.api.dtos.ErrorDTO;
import com.todolist.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDTO> EmailAlreadyExistHandler(EmailAlreadyExistException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<ErrorDTO> UsernameAlreadyExistHandler(UsernameAlreadyExistException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorDTO> UnauthorizedUserHandler(UnauthorizedUserException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorDTO> InvalidDateRangeHandler(InvalidDateRangeException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(TaskDoesNotExistException.class)
    public ResponseEntity<ErrorDTO> TaskDoesNotExistHandler(TaskDoesNotExistException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorDTO> InvalidStatusHandler(InvalidStatusException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidPriorityException.class)
    public ResponseEntity<ErrorDTO> InvalidPriorityHandler(InvalidPriorityException ex){
        ErrorDTO message = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
