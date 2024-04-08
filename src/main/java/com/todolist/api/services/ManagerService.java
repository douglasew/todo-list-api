package com.todolist.api.services;

import com.todolist.api.domain.role.Role;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.dtos.UserResponseDTO;
import com.todolist.api.exceptions.EmailAlreadyExistException;
import com.todolist.api.exceptions.UsernameAlreadyExistException;
import com.todolist.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsService;

     public ResponseEntity<Object> create(UserRequestDTO data){
        if(this.userDetailsService.loadUserByUsername(data.getUsername()) != null){
            throw new UsernameAlreadyExistException();
        }

        if(this.userRepository.findByEmail(data.getEmail()) != null){
            throw new EmailAlreadyExistException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        data.setPassword(encryptedPassword);
        data.setRole(new Role(1L,"ADMIN"));

         User newUser = new User(data);

         this.userRepository.save(newUser);

         return ResponseEntity.status(HttpStatus.CREATED).build();
     }

    public ResponseEntity<Object> index() {
         var users = this.userRepository.findAll();

         List<UserResponseDTO> details = users.stream().map(user -> new UserResponseDTO(
                 user.getName(),
                 user.getUsername(),
                 user.getEmail(),
                 user.getPhoto(),
                 user.getRole().getName()
         )).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(details);
    }
}

