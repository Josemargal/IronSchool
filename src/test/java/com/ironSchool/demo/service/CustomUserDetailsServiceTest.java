package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.model.UserAdmin;
import com.ironSchool.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername_Success() {
        // Preparar datos de prueba
        String email = "test@example.com";

        Student student = new Student();
        student.setId(1L);
        student.setEmail(email);
        student.setPassword("encoded_password");

        when(userRepository.findByEmail(email)).thenReturn(student);

        // Ejecutar
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Verificar
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encoded_password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Preparar datos de prueba
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        // Ejecutar y verificar
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }
}
