package com.todolist.api.services;

import com.todolist.api.domain.role.Role;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.dtos.UserResponseDTO;
import com.todolist.api.exceptions.EmailAlreadyExistException;
import com.todolist.api.exceptions.UsernameAlreadyExistException;
import com.todolist.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
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

    public Page<UserResponseDTO> index(int page, int size, Pageable pageable) {
        Sort sort = Sort.by(
                Sort.Order.asc("role")
        );

        pageable = PageRequest.of(page,size,sort);

        Page<User> users = this.userRepository.findAll(pageable);

        return users.map(user -> new UserResponseDTO(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoto(),
                user.getRole().getName()
        ));
    }
}
