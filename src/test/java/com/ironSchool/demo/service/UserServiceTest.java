package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.model.UserAdmin;
import com.ironSchool.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        // Preparar datos de prueba
        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("test@example.com");
        student.setPassword("password123");

        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Test Student");
        savedStudent.setEmail("test@example.com");
        savedStudent.setPassword("encoded_password");

        when(userRepository.save(any(UserAdmin.class))).thenReturn(savedStudent);

        // Ejecutar
        UserAdmin result = userService.saveUser(student);

        // Verificar
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encoded_password", result.getPassword());

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(UserAdmin.class));
    }

    @Test
    public void testFindByEmail() {
        // Preparar datos de prueba
        String email = "test@example.com";

        Student student = new Student();
        student.setId(1L);
        student.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(student);

        // Ejecutar
        UserAdmin result = userService.findByEmail(email);

        // Verificar
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(email, result.getEmail());

        verify(userRepository).findByEmail(email);
    }

    @Test
    public void testCheckPassword() {
        // Preparar datos de prueba
        UserAdmin user = new Student();
        user.setPassword("encoded_password");

        String rawPassword = "password123";

        when(passwordEncoder.matches(rawPassword, "encoded_password")).thenReturn(true);

        // Ejecutar
        boolean result = userService.checkPassword(user, rawPassword);

        // Verificar
        assertTrue(result);

        verify(passwordEncoder).matches(rawPassword, "encoded_password");
    }
}
