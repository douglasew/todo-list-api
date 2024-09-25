package com.todolist.api.dtos;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequestDTO(
        @NotEmpty(message = "Insira o username")
        String username,

        @NotEmpty(message = "Insira a senha")
        String password
) {}
