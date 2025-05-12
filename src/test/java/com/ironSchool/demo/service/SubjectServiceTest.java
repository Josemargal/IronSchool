package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void testGetAllSubjects_returnsEmptyList_whenNoneExist() {
        when(subjectRepository.findAll()).thenReturn(Collections.emptyList());

        List<Subject> subjects = subjectService.getAllSubjects();
        assertTrue(subjects.isEmpty());
    }

    @Test
    public void testGetSubjectById_returnsSubject_whenExists() {
        Subject subject = new Subject();
        subject.setId(1L);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        Subject result = subjectService.getSubjectById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testSaveSubject_persistsToDatabase() {
        Subject subject = new Subject();
        subject.setName("Física");

        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        Subject saved = subjectService.saveSubject(subject);
        assertNotNull(saved.getName());
    }
}
