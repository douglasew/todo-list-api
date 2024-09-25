package com.todolist.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.api.domain.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotEmpty(message = "Insira o nome")
    @Size(max = 50, message = "Nome muito longo")
    private String name;

    @NotEmpty(message = "Insira o username")
    @Size(max = 50, message = "Username muito longo")
    @Pattern(regexp = "\\S+", message = "O username não pode conter espaços em branco")
    @Pattern(
            regexp = "^[a-zA-Z0-9]*$",
            message = "O username não pode conter acentos"
    )
    private String username;

    @Email(message = "Insira um email válido")
    @NotEmpty(message = "Insira o email")
    @Size(max = 100, message = "email muito longo")
    private String email;

    private String photo;

    @NotEmpty(message = "Insira a senha")
    @Size(max = 70, message = "Password muito longo")
    @Pattern(regexp = "\\S+", message = "A senha não pode conter espaços em branco")
    @Pattern(
            //regexp = "[^\\p{M}]*",
            regexp = "^[a-zA-Z0-9]*$",
            message = "A senha não pode conter acentos"
    )
    private String password;

    @JsonIgnore
    private Role role;
}
