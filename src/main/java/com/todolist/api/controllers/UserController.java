package com.todolist.api.controllers;

import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.dtos.UserResponseDTO;
import com.todolist.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserResponseDTO> show(){
        return this.userService.show();
    }

    @PatchMapping("/")
    public ResponseEntity<Object> edit(@RequestBody @Valid UserRequestDTO data){
        return this.userService.edit(data);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> delete(){
        return this.userService.delete();
    }

    @PostMapping("/photo")
    public String UpdateProfilePhoto(@RequestParam("image")MultipartFile file) throws IOException {
        return this.userService.uploadProfilePhoto(file);
    }
}
