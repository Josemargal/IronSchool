package com.ironSchool.demo.controller;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSubjects_ReturnsListOfSubjects() {
        // Arrange
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("Matemáticas");

        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Historia");

        List<Subject> subjects = Arrays.asList(subject1, subject2);

        when(subjectService.getAllSubjects()).thenReturn(subjects);

        // Act
        List<Subject> result = subjectController.getAllSubjects();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Matemáticas", result.get(0).getName());
        assertEquals("Historia", result.get(1).getName());
        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void getSubjectById_ExistingId_ReturnsSubject() {
        // Arrange
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Matemáticas");

        when(subjectService.getSubjectById(1L)).thenReturn(Optional.of(subject));

        // Act
        ResponseEntity<Subject> response = subjectController.getSubjectById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Matemáticas", response.getBody().getName());
        verify(subjectService, times(1)).getSubjectById(1L);
    }

    @Test
    void getSubjectById_NonExistingId_ReturnsNotFound() {
        // Arrange
        when(subjectService.getSubjectById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Subject> response = subjectController.getSubjectById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(subjectService, times(1)).getSubjectById(99L);
    }

    @Test
    void createSubject_Success() {
        // Arrange
        Subject subjectToCreate = new Subject();
        subjectToCreate.setName("Ciencias");

        Subject savedSubject = new Subject();
        savedSubject.setId(3L);
        savedSubject.setName("Ciencias");

        when(subjectService.saveSubject(any(Subject.class))).thenReturn(savedSubject);

        // Act
        Subject result = subjectController.createSubject(subjectToCreate);

        // Assert
        assertEquals(3L, result.getId());
        assertEquals("Ciencias", result.getName());
        verify(subjectService, times(1)).saveSubject(subjectToCreate);
    }

    @Test
    void updateSubject_Success() {
        // Arrange
        Subject subjectDetails = new Subject();
        subjectDetails.setName("Matemáticas Avanzadas");

        Subject updatedSubject = new Subject();
        updatedSubject.setId(1L);
        updatedSubject.setName("Matemáticas Avanzadas");

        when(subjectService.updateSubject(eq(1L), any(Subject.class))).thenReturn(updatedSubject);

        // Act
        Subject result = subjectController.updateSubject(1L, subjectDetails);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Matemáticas Avanzadas", result.getName());
        verify(subjectService, times(1)).updateSubject(eq(1L), any(Subject.class));
    }

    @Test
    void deleteSubject_CallsServiceMethod() {
        // Act
        subjectController.deleteSubject(1L);

        // Assert
        verify(subjectService, times(1)).deleteSubject(1L);
    }
}
