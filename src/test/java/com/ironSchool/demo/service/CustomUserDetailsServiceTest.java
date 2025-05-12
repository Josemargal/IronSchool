package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_returnsUser() {
        Student student = new Student();
        student.setEmail("ana@example.com");

        when(userRepository.findByEmail("ana@example.com")).thenReturn(student);

        var userDetails = userDetailsService.loadUserByUsername("ana@example.com");
        assertNotNull(userDetails);
        assertEquals("ana@example.com", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_throwsException_whenNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("notfound@example.com");
        });
    }
}
