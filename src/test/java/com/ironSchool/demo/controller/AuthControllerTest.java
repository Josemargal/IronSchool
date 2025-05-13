package com.ironSchool.demo.controller;

import com.ironSchool.demo.config.JwtUtil;
import com.ironSchool.demo.dto.AuthRequest;
import com.ironSchool.demo.dto.AuthResponse;
import com.ironSchool.demo.dto.RegisterRequest;
import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.model.Teacher;
import com.ironSchool.demo.model.UserAdmin;
import com.ironSchool.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Student_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setUserType("STUDENT");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("John Doe");
        savedStudent.setEmail("john@example.com");

        when(userService.saveUser(any(Student.class))).thenReturn(savedStudent);

        // Act
        ResponseEntity<String> response = authController.registerUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario registrado exitosamente. ID: 1", response.getBody());
        verify(userService, times(1)).saveUser(any(Student.class));
    }

    @Test
    void registerUser_Teacher_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("Jane Smith");
        request.setEmail("jane@example.com");
        request.setPassword("password");
        request.setUserType("TEACHER");

        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(2L);
        savedTeacher.setName("Jane Smith");
        savedTeacher.setEmail("jane@example.com");

        when(userService.saveUser(any(Teacher.class))).thenReturn(savedTeacher);

        // Act
        ResponseEntity<String> response = authController.registerUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario registrado exitosamente. ID: 2", response.getBody());
        verify(userService, times(1)).saveUser(any(Teacher.class));
    }

    @Test
    void registerUser_MissingFields_BadRequest() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        // Missing email, password and userType

        // Act
        ResponseEntity<String> response = authController.registerUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Todos los campos son obligatorios", response.getBody());
        verify(userService, never()).saveUser(any());
    }

    @Test
    void registerUser_InvalidUserType_BadRequest() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setUserType("INVALID_TYPE");

        // Act
        ResponseEntity<String> response = authController.registerUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Tipo de usuario inválido", response.getBody());
        verify(userService, never()).saveUser(any());
    }

    @Test
    void loginUser_Success() throws Exception {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("john@example.com");
        authRequest.setPassword("password");

        UserDetails userDetails = new User("john@example.com", "password", new ArrayList<>());

        // Crear un mock de Student que responda correctamente a getUserType()
        Student student = mock(Student.class);
        when(student.getUserType()).thenReturn("STUDENT");

        when(userDetailsService.loadUserByUsername("john@example.com")).thenReturn(userDetails);
        when(userService.findByEmail("john@example.com")).thenReturn(student);
        when(jwtUtil.generateToken(any(UserDetails.class), eq("STUDENT"))).thenReturn("jwt-token");

        // Act
        ResponseEntity<AuthResponse> response = authController.loginUser(authRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        assertEquals("STUDENT", response.getBody().getUserType());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginUser_InvalidCredentials_ThrowsException() {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("john@example.com");
        authRequest.setPassword("wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            authController.loginUser(authRequest);
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}
