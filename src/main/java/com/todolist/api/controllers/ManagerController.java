package com.todolist.api.controllers;

import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.dtos.UserResponseDTO;
import com.todolist.api.services.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/hello")
    public String hello(){
        return "hello ADMIN";
    }

    @GetMapping("/")
    public Page<UserResponseDTO> index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Pageable pageable){
        return this.managerService.index(page, size, pageable);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody @Valid UserRequestDTO data){
        return this.managerService.create(data);
    }
}
