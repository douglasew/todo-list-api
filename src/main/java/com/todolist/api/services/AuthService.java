package com.todolist.api.services;

import com.todolist.api.domain.role.Role;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.AuthRequestDTO;
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

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<AuthResponseDTO> login(AuthRequestDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = jwtService.generateToken(auth);
        var authToken = new AuthResponseDTO(token);

        return ResponseEntity.ok().body(authToken);
    }

    public ResponseEntity<Object> register(UserRequestDTO data){
        if(this.userDetailsService.loadUserByUsername(data.getUsername()) != null){
            throw new UsernameAlreadyExistException();
        }

        if(this.userRepository.findByEmail(data.getEmail()) != null){
            throw new EmailAlreadyExistException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        data.setPassword(encryptedPassword);
        data.setRole(Role.Values.USER.toRole());
        var newuser = new User(data);

        this.userRepository.save(newuser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
