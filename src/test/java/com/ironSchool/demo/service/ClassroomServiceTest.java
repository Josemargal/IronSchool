package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.repository.ClassroomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @InjectMocks
    private ClassroomService classroomService;

    @Test
    public void testGetAllClassrooms() {
        // Preparar datos de prueba
        Classroom classroom1 = new Classroom();
        classroom1.setId(1L);
        classroom1.setRoomNumber("A101");

        Classroom classroom2 = new Classroom();
        classroom2.setId(2L);
        classroom2.setRoomNumber("B202");

        when(classroomRepository.findAll()).thenReturn(Arrays.asList(classroom1, classroom2));

        // Ejecutar
        List<Classroom> result = classroomService.getAllClassrooms();

        // Verificar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("A101", result.get(0).getRoomNumber());
        assertEquals("B202", result.get(1).getRoomNumber());

        verify(classroomRepository).findAll();
    }

    @Test
    public void testGetClassroomById() {
        // Preparar datos de prueba
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setRoomNumber("A101");

        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));

        // Ejecutar
        Optional<Classroom> result = classroomService.getClassroomById(1L);

        // Verificar
        assertTrue(result.isPresent());
        assertEquals("A101", result.get().getRoomNumber());

        verify(classroomRepository).findById(1L);
    }

    @Test
    public void testSaveClassroom() {
        // Preparar datos de prueba
        Classroom classroom = new Classroom();
        classroom.setRoomNumber("A101");
        classroom.setCapacity(30);

        Classroom savedClassroom = new Classroom();
        savedClassroom.setId(1L);
        savedClassroom.setRoomNumber("A101");
        savedClassroom.setCapacity(30);

        when(classroomRepository.save(any(Classroom.class))).thenReturn(savedClassroom);

        // Ejecutar
        Classroom result = classroomService.saveClassroom(classroom);

        // Verificar
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A101", result.getRoomNumber());
        assertEquals(30, result.getCapacity());

        verify(classroomRepository).save(classroom);
    }

    @Test
    public void testUpdateClassroom_Success() {
        // Preparar datos de prueba
        Long id = 1L;

        Classroom updatedClassroom = new Classroom();
        updatedClassroom.setRoomNumber("A101-Updated");
        updatedClassroom.setCapacity(40);

        Classroom savedClassroom = new Classroom();
        savedClassroom.setId(id);
        savedClassroom.setRoomNumber("A101-Updated");
        savedClassroom.setCapacity(40);

        when(classroomRepository.existsById(id)).thenReturn(true);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(savedClassroom);

        // Ejecutar
        Classroom result = classroomService.updateClassroom(id, updatedClassroom);

        // Verificar
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("A101-Updated", result.getRoomNumber());
        assertEquals(40, result.getCapacity());

        verify(classroomRepository).existsById(id);
        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    public void testUpdateClassroom_NotFound() {
        // Preparar datos de prueba
        Long id = 1L;

        Classroom updatedClassroom = new Classroom();
        updatedClassroom.setRoomNumber("A101-Updated");

        when(classroomRepository.existsById(id)).thenReturn(false);

        // Ejecutar
        Classroom result = classroomService.updateClassroom(id, updatedClassroom);

        // Verificar
        assertNull(result);

        verify(classroomRepository).existsById(id);
        verify(classroomRepository, never()).save(any(Classroom.class));
    }

    @Test
    public void testDeleteClassroom_Success() {
        // Preparar datos de prueba
        Long id = 1L;

        when(classroomRepository.existsById(id)).thenReturn(true);
        doNothing().when(classroomRepository).deleteById(id);

        // Ejecutar
        boolean result = classroomService.deleteClassroom(id);

        // Verificar
        assertTrue(result);

        verify(classroomRepository).existsById(id);
        verify(classroomRepository).deleteById(id);
    }

    @Test
    public void testDeleteClassroom_NotFound() {
        // Preparar datos de prueba
        Long id = 1L;

        when(classroomRepository.existsById(id)).thenReturn(false);

        // Ejecutar
        boolean result = classroomService.deleteClassroom(id);

        // Verificar
        assertFalse(result);

        verify(classroomRepository).existsById(id);
        verify(classroomRepository, never()).deleteById(any());
    }
}
