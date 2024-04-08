package com.todolist.api.services;

import com.todolist.api.domain.task.Task;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.TaskRequestDTO;
import com.todolist.api.dtos.TaskRequestUpdateDTO;
import com.todolist.api.dtos.TaskResponseDTO;
import com.todolist.api.exceptions.InvalidDateRangeException;
import com.todolist.api.exceptions.PreviousDateTaskException;
import com.todolist.api.exceptions.TaskDoesNotExistException;
import com.todolist.api.exceptions.UnauthorizedUserException;
import com.todolist.api.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class  TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<Object> create(TaskRequestDTO data){
        User user = user();

        dateValidation(data);
        data.setUser((User) user);
        var newTask = new Task(data);
        this.taskRepository.save(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public List<TaskResponseDTO> index(){
        var user = user();

        List<Task> tasks = this.taskRepository.findByUser(user);
        List<TaskResponseDTO> taskResponses = new ArrayList<>();

        for (Task task : tasks) {
            TaskResponseDTO taskResponse = new TaskResponseDTO(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority().getName(),
                    task.getStartAt(),
                    task.getEndAt()
            );
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    public ResponseEntity<TaskResponseDTO> show(String id){
        var task = taskRepository.findById(id).orElse(null);
        userPermission(id);

        assert task != null;

        TaskResponseDTO taskResponse = new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getName(),
                task.getStartAt(),
                task.getEndAt()
        );

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    public  ResponseEntity<TaskRequestUpdateDTO> update(String id, TaskRequestUpdateDTO data){
        var task = this.taskRepository.findById(id).orElse(null);
        userPermission(id);

        assert task != null;

        if(data.title() != null){
            task.setTitle(data.title());
        }
        if(data.title() != null){
            task.setDescription(data.description());
        }
        if(data.priority() != null){
            task.setPriority(data.priority());
        }
        if(data.startAt() != null){
            task.setStartAt(data.startAt());
        }
        if(data.endAt() != null){
            task.setEndAt(data.endAt());
        }

        this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<String> delete(String id){
        userPermission(id);

        this.taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Atividade deletada");
    }

    private User user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        return (User) userDetails;
    }

    private void userPermission(String id){
        var task = this.taskRepository.findById(id).orElseThrow(TaskDoesNotExistException::new);
        var user = user();

        if(!task.getUser().equals(user)){
            throw new UnauthorizedUserException();
        }
    }

    private void dateValidation(TaskRequestDTO data){
        Date currentDate = new Date();

        if(data.getStartAt().getTime() < currentDate.getTime()){
            throw new PreviousDateTaskException();
        }

        if(data.getEndAt().getTime() < data.getStartAt().getTime()){
            throw new InvalidDateRangeException();
        }
    }
}
