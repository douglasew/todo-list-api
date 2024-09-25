package com.todolist.api.services;

import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.AuthRequestDTO;
import com.todolist.api.dtos.AuthResponseDTO;
import com.todolist.api.dtos.UserRequestDTO;

import com.todolist.api.exceptions.EmailAlreadyExistException;
import com.todolist.api.exceptions.UsernameAlreadyExistException;
import com.todolist.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @InjectMocks
    private AuthService authService;


    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loginSuccess() {
        AuthRequestDTO authDTO = new AuthRequestDTO("teste", "12345");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authDTO.username(), authDTO.password());
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(token)).thenReturn(auth);

        String generatedToken = "generatedToken";
        when(jwtService.generateToken(auth)).thenReturn(generatedToken);

        ResponseEntity<AuthResponseDTO> response = authService.login(authDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(generatedToken, response.getBody().token());

        verify(authenticationManager, times(1)).authenticate(token);
        verify(jwtService, times(1)).generateToken(auth);
    }

    @Test
    @DisplayName("should return error when logging in with wrong credentials")
    void loginFailure() {
        AuthRequestDTO authDTO = new AuthRequestDTO("teste", "12345");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authDTO.username(), authDTO.password());
        when(authenticationManager.authenticate(token)).thenThrow(new BadCredentialsException("Invalid credentials"));
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.login(authDTO));

        assertEquals("Invalid credentials", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(token);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("should create a new user in the system")
    void registerSuccess() {
        UserRequestDTO data = new UserRequestDTO();
        data.setName("user");
        data.setUsername("user");
        data.setEmail("teste@teste.com");
        data.setPassword("12345");

        when(userDetailsService.loadUserByUsername(data.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(data.getEmail())).thenReturn(null);

        ResponseEntity<Object> response = authService.register(data);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(userDetailsService, times(1)).loadUserByUsername(data.getUsername());
        verify(userRepository, times(1)).findByEmail(data.getEmail());
        verify(userRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("should give an exception when an email already exists")
    void RegisterEmailAlreadyExists() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        UserRequestDTO existingEmailRequest = new UserRequestDTO();
        existingEmailRequest.setEmail("existing@example.com");

        EmailAlreadyExistException exception = assertThrows(EmailAlreadyExistException.class,
                () -> authService.register(existingEmailRequest));

        verify(userRepository, never()).save(any());

        assertEquals("E-mail already registered", exception.getMessage());
    }

    @Test
    @DisplayName("should give an exception when an username already exists")
    void RegisterUsernameAlreadyExists(){
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        UserRequestDTO existingUsernameRequest = new UserRequestDTO();
        existingUsernameRequest.setUsername("teste");

        UsernameAlreadyExistException exception = assertThrows(UsernameAlreadyExistException.class,
                () -> authService.register(existingUsernameRequest));

        verify(userRepository, never()).save(any());

        assertEquals("Username already registered", exception.getMessage());
    }

}
