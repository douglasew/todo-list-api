package com.todolist.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthDTO {

    @NotEmpty(message = "Enter the username")
    private String username;

    @NotEmpty(message = "Enter the password")
    private String password;

}
