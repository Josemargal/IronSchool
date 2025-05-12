package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_returnsStudent() {
        Student student = new Student();
        student.setEmail("student@example.com");

        when(userRepository.findByEmail("student@example.com")).thenReturn(student);

        var result = userService.findByEmail("student@example.com");
        assertNotNull(result);
        assertEquals("student@example.com", result.getEmail());
    }

    @Test
    public void testSaveUser_student() {
        Student student = new Student();
        student.setEmail("new@example.com");

        when(userRepository.save(any(Student.class))).thenReturn(student);

        var result = userService.saveUser(student);
        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
    }
}
