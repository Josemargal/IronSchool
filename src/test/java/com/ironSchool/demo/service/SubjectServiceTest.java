package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.repository.SubjectRepository;
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
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    public void testGetAllSubjects() {
        // Preparar datos de prueba
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("Matemáticas");

        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Historia");

        when(subjectRepository.findAll()).thenReturn(Arrays.asList(subject1, subject2));

        // Ejecutar
        List<Subject> result = subjectService.getAllSubjects();

        // Verificar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Matemáticas", result.get(0).getName());
        assertEquals("Historia", result.get(1).getName());

        verify(subjectRepository).findAll();
    }

    @Test
    public void testGetSubjectById() {
        // Preparar datos de prueba
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Matemáticas");
        subject.setSchedule("Lunes y Miércoles 10:00-12:00");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        // Ejecutar
        Optional<Subject> result = subjectService.getSubjectById(1L);

        // Verificar
        assertTrue(result.isPresent());
        assertEquals("Matemáticas", result.get().getName());
        assertEquals("Lunes y Miércoles 10:00-12:00", result.get().getSchedule());

        verify(subjectRepository).findById(1L);
    }

    @Test
    public void testSaveSubject() {
        // Preparar datos de prueba
        Subject subject = new Subject();
        subject.setName("Ciencias");
        subject.setSchedule("Viernes 09:00-13:00");

        Subject savedSubject = new Subject();
        savedSubject.setId(1L);
        savedSubject.setName("Ciencias");
        savedSubject.setSchedule("Viernes 09:00-13:00");

        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        // Ejecutar
        Subject result = subjectService.saveSubject(subject);

        // Verificar
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ciencias", result.getName());
        assertEquals("Viernes 09:00-13:00", result.getSchedule());

        verify(subjectRepository).save(subject);
    }

    @Test
    public void testUpdateSubject_Success() {
        // Preparar datos de prueba
        Long id = 1L;

        Subject existingSubject = new Subject();
        existingSubject.setId(id);
        existingSubject.setName("Matemáticas");
        existingSubject.setSchedule("Lunes y Miércoles 10:00-12:00");

        Subject updatedDetails = new Subject();
        updatedDetails.setName("Matemáticas Avanzadas");
        updatedDetails.setSchedule("Lunes y Miércoles 14:00-16:00");

        Subject savedSubject = new Subject();
        savedSubject.setId(id);
        savedSubject.setName("Matemáticas Avanzadas");
        savedSubject.setSchedule("Lunes y Miércoles 14:00-16:00");

        when(subjectRepository.findById(id)).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        // Ejecutar
        Subject result = subjectService.updateSubject(id, updatedDetails);

        // Verificar
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Matemáticas Avanzadas", result.getName());
        assertEquals("Lunes y Miércoles 14:00-16:00", result.getSchedule());

        verify(subjectRepository).findById(id);
        verify(subjectRepository).save(any(Subject.class));
    }

    @Test
    public void testUpdateSubject_NotFound() {
        // Preparar datos de prueba
        Long id = 1L;

        Subject updatedDetails = new Subject();
        updatedDetails.setName("Matemáticas Avanzadas");

        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        // Ejecutar
        Subject result = subjectService.updateSubject(id, updatedDetails);

        // Verificar
        assertNull(result);

        verify(subjectRepository).findById(id);
        verify(subjectRepository, never()).save(any(Subject.class));
    }

    @Test
    public void testDeleteSubject() {
        // Preparar datos de prueba
        Long id = 1L;

        doNothing().when(subjectRepository).deleteById(id);

        // Ejecutar
        subjectService.deleteSubject(id);

        // Verificar
        verify(subjectRepository).deleteById(id);
    }
}
