package com.todolist.api.controllers;

import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.services.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Object> index(){
        return this.managerService.index();
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody @Valid UserRequestDTO data){
        return this.managerService.create(data);
    }
}
