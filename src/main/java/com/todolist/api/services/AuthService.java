package com.todolist.api.services;

import com.todolist.api.domain.role.Role;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.AuthDTO;
import com.todolist.api.dtos.AuthResponseDTO;
import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.exceptions.EmailAlreadyExistException;
import com.todolist.api.exceptions.UsernameAlreadyExistException;
import com.todolist.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<Object> login(AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());

        var auth = authenticationManager.authenticate(usernamePassword);

        if(!auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        var token = jwtService.generateToken(auth);
        var authToken = new AuthResponseDTO(token);

        return ResponseEntity.ok().body(authToken);
    }

    public ResponseEntity<Object> register(UserRequestDTO data){
        if(this.userRepository.findByUsername(data.getUsername()) != null){
            throw new UsernameAlreadyExistException();
        }

        if(this.userRepository.findByEmail(data.getEmail()) != null){
            throw new EmailAlreadyExistException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        data.setPassword(encryptedPassword);
        data.setRole(new Role(2L, "USER"));
        var newuser = new User(data);

        this.userRepository.save(newuser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
