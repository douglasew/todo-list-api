package com.todolist.api.services;

import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.UserRequestDTO;
import com.todolist.api.dtos.UserResponseDTO;
import com.todolist.api.exceptions.EmailAlreadyExistException;
import com.todolist.api.exceptions.UsernameAlreadyExistException;
import com.todolist.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\storage" + "\\uploads\\";

    public ResponseEntity<UserResponseDTO> show(){
        User user = user();

        var details = new UserResponseDTO(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoto(),
                user.getRole().getName()
        );
        return ResponseEntity.status(HttpStatus.OK).body(details);
    }

    public ResponseEntity<Object> edit(UserRequestDTO data){
        User user = user();

        if(!data.getUsername().equals(user.getUsername()) && this.userDetailsService.loadUserByUsername(data.getUsername()) != null){
            throw new UsernameAlreadyExistException();
        }

        if(!data.getEmail().equals(user.getEmail()) && this.userRepository.findByEmail(data.getEmail()) != null){
            throw new EmailAlreadyExistException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        user.setName(data.getName());
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(encryptedPassword);

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> delete(){
        User user = user();

        var userId = user.getId();
        this.userRepository.deleteById(userId);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado");
    }

    public String uploadProfilePhoto(MultipartFile file) throws IOException {
        User user = user();

        String fileNameAndPath = UPLOAD_DIRECTORY + user.getId() + file.getOriginalFilename();

        user.setPhoto(fileNameAndPath);

        file.transferTo(new File(fileNameAndPath));
        this.userRepository.save(user);

        return "file uploaded successfully : " + fileNameAndPath;
    }

    private User user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        return (User) userDetails;
    }
}

