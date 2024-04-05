package com.todolist.api.controllers;

import com.todolist.api.dtos.AuthDTO;
import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO data){
       return this.authService.login(data);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRequestDTO data){
        return this.authService.register(data);
    }
}
