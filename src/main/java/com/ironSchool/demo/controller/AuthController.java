package com.ironSchool.demo.controller;

import com.ironSchool.demo.config.JwtUtil;
import com.ironSchool.demo.dto.AuthRequest;
import com.ironSchool.demo.dto.AuthResponse;
import com.ironSchool.demo.dto.RegisterRequest;
import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.model.Teacher;
import com.ironSchool.demo.model.UserAdmin;
import com.ironSchool.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    // 🔐 Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        if (request.getName() == null || request.getEmail() == null || request.getPassword() == null || request.getUserType() == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        }

        UserAdmin newUser;

        if ("STUDENT".equalsIgnoreCase(request.getUserType())) {
            Student student = new Student();
            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student.setPassword(request.getPassword());
            student.setGrade(1);
            newUser = userService.saveUser(student);
        } else if ("TEACHER".equalsIgnoreCase(request.getUserType())) {
            Teacher teacher = new Teacher();
            teacher.setName(request.getName());
            teacher.setEmail(request.getEmail());
            teacher.setPassword(request.getPassword());
            teacher.setDepartment("Sin asignar");
            newUser = userService.saveUser(teacher);
        } else {
            return ResponseEntity.badRequest().body("Tipo de usuario inválido");
        }

        return ResponseEntity.ok("Usuario registrado exitosamente. ID: " + newUser.getId());
    }

    // 🔑 Iniciar sesión y obtener token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales inválidas", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails, getUserType(authRequest.getEmail()));

        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        response.setUserType(getUserType(authRequest.getEmail()));
        return ResponseEntity.ok(response);
    }

    // Método auxiliar para obtener tipo de usuario
    private String getUserType(String email) {
        UserAdmin user = userService.findByEmail(email);
        return user.getUserType();
    }
}
