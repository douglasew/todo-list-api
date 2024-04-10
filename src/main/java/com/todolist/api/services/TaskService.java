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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public Page<TaskResponseDTO> index(int page, int size, Pageable pageable){
        var user = user();

        Sort sort = Sort.by(
                Sort.Order.desc("createdAt"),
                Sort.Order.asc("priority")
        );

        pageable = PageRequest.of(page, size, sort);

        Page<Task> tasks = this.taskRepository.findByUser(user, pageable);

        return tasks.map(task -> new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getName(),
                task.getStartAt(),
                task.getEndAt()
        ));
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
        Task task = this.taskRepository.findById(id).orElseThrow(TaskDoesNotExistException::new);
        userPermission(id);

        task.setTitle(data.title());
        task.setDescription(data.description());
        task.setPriority(data.priority());
        task.setStartAt(data.startAt());
        task.setEndAt(data.endAt());

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
