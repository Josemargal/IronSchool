package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentRepository repo;
    private StudentService service;

    @BeforeEach
    void setUp() {
        repo = mock(StudentRepository.class);
        service = new StudentService(repo);
    }

    @Test
    void testFindById_StudentExists() {
        Student student = new Student();
        student.setId(1L);
        student.setFullName("Juan");

        when(repo.findById(1L)).thenReturn(Optional.of(student));

        Student result = service.findById(1L);
        assertEquals("Juan", result.getFullName());
    }

    @Test
    void testFindAll_ReturnsList() {
        when(repo.findAll()).thenReturn(List.of(new Student(), new Student()));

        List<Student> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testDelete_CallsRepository() {
        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}

