package com.todolist.api.dtos;

import com.todolist.api.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotEmpty(message = "Enter the name")
    @Size(max = 50, message = "Name very long")
    private String name;

    @NotEmpty(message = "Enter the username")
    @Size(max = 50, message = "Username very long")
    @Pattern(regexp = "\\S+", message = "The username cannot contain blanks")
    @Pattern(regexp = "[^\\p{M}]*", message = "The username cannot contain accents")
    private String username;

    @Email(message = "Enter a valid email")
    @NotEmpty(message = "Enter the email")
    @Size(max = 100, message = "email very long")
    private String email;

    private String photo;

    @NotEmpty(message = "Enter the password")
    @Size(max = 70, message = "Password very long")
    @Pattern(regexp = "\\S+", message = "The password cannot contain blanks")
    private String password;

    @JsonIgnore
    private Role role;
}
