package com.ironSchool.demo.controller;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.service.ClassroomService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClassroomControllerTest {

    @Mock
    private ClassroomService classroomService;

    @InjectMocks
    private ClassroomController classroomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClassrooms_ReturnsListOfClassrooms() {
        // Arrange
        Classroom classroom1 = new Classroom();
        classroom1.setId(1L);
        classroom1.setRoomNumber("101");
        classroom1.setCapacity(30);

        Classroom classroom2 = new Classroom();
        classroom2.setId(2L);
        classroom2.setRoomNumber("102");
        classroom2.setCapacity(25);

        List<Classroom> classrooms = Arrays.asList(classroom1, classroom2);

        when(classroomService.getAllClassrooms()).thenReturn(classrooms);

        // Act
        List<Classroom> result = classroomController.getAllClassrooms();

        // Assert
        assertEquals(2, result.size());
        assertEquals("101", result.get(0).getRoomNumber());
        assertEquals(30, result.get(0).getCapacity());
        assertEquals("102", result.get(1).getRoomNumber());
        assertEquals(25, result.get(1).getCapacity());
        verify(classroomService, times(1)).getAllClassrooms();
    }

    @Test
    void getClassroomById_ExistingId_ReturnsClassroom() {
        // Arrange
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setRoomNumber("101");
        classroom.setCapacity(30);

        when(classroomService.getClassroomById(1L)).thenReturn(Optional.of(classroom));

        // Act
        ResponseEntity<Classroom> response = classroomController.getClassroomById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("101", response.getBody().getRoomNumber());
        assertEquals(30, response.getBody().getCapacity());
        verify(classroomService, times(1)).getClassroomById(1L);
    }

    @Test
    void getClassroomById_NonExistingId_ReturnsNotFound() {
        // Arrange
        when(classroomService.getClassroomById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Classroom> response = classroomController.getClassroomById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(classroomService, times(1)).getClassroomById(99L);
    }

    @Test
    void createClassroom_Success() {
        // Arrange
        Classroom classroomToCreate = new Classroom();
        classroomToCreate.setRoomNumber("201");
        classroomToCreate.setCapacity(40);

        Classroom savedClassroom = new Classroom();
        savedClassroom.setId(3L);
        savedClassroom.setRoomNumber("201");
        savedClassroom.setCapacity(40);

        when(classroomService.saveClassroom(any(Classroom.class))).thenReturn(savedClassroom);

        // Act
        ResponseEntity<Classroom> response = classroomController.createClassroom(classroomToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(3L, response.getBody().getId());
        assertEquals("201", response.getBody().getRoomNumber());
        assertEquals(40, response.getBody().getCapacity());
        verify(classroomService, times(1)).saveClassroom(classroomToCreate);
    }

    @Test
    void updateClassroom_ExistingId_Success() {
        // Arrange
        Classroom classroomDetails = new Classroom();
        classroomDetails.setRoomNumber("101A");
        classroomDetails.setCapacity(35);

        Classroom updatedClassroom = new Classroom();
        updatedClassroom.setId(1L);
        updatedClassroom.setRoomNumber("101A");
        updatedClassroom.setCapacity(35);

        when(classroomService.updateClassroom(eq(1L), any(Classroom.class))).thenReturn(updatedClassroom);

        // Act
        ResponseEntity<Classroom> response = classroomController.updateClassroom(1L, classroomDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("101A", response.getBody().getRoomNumber());
        assertEquals(35, response.getBody().getCapacity());
        verify(classroomService, times(1)).updateClassroom(eq(1L), any(Classroom.class));
    }

    @Test
    void updateClassroom_NonExistingId_ReturnsNotFound() {
        // Arrange
        Classroom classroomDetails = new Classroom();
        classroomDetails.setRoomNumber("999");
        classroomDetails.setCapacity(50);

        when(classroomService.updateClassroom(eq(99L), any(Classroom.class))).thenReturn(null);

        // Act
        ResponseEntity<Classroom> response = classroomController.updateClassroom(99L, classroomDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(classroomService, times(1)).updateClassroom(eq(99L), any(Classroom.class));
    }

    @Test
    void deleteClassroom_ExistingId_ReturnsNoContent() {
        // Arrange
        when(classroomService.deleteClassroom(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = classroomController.deleteClassroom(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(classroomService, times(1)).deleteClassroom(1L);
    }

    @Test
    void deleteClassroom_NonExistingId_ReturnsNotFound() {
        // Arrange
        when(classroomService.deleteClassroom(99L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = classroomController.deleteClassroom(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(classroomService, times(1)).deleteClassroom(99L);
    }
}
