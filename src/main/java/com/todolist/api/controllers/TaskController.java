package com.todolist.api.controllers;

import java.util.List;

import com.todolist.api.dtos.TaskRequestDTO;
import com.todolist.api.dtos.TaskRequestUpdateDTO;
import com.todolist.api.dtos.TaskResponseDTO;
import com.todolist.api.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody @Valid TaskRequestDTO data) {
        return this.taskService.create(data);
    }

    @GetMapping("/")
    public List<TaskResponseDTO> index() {
        return this.taskService.index();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable String id) {
        return this.taskService.show(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody TaskRequestUpdateDTO data){
        return  this.taskService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return this.taskService.delete(id);
    }
}
