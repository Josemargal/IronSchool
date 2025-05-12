package com.ironSchool.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Test
    public void testGetAllSubjects() throws Exception {
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("Matemáticas");

        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Historia");

        when(subjectService.getAllSubjects()).thenReturn(List.of(subject1, subject2));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Matemáticas"))
                .andExpect(jsonPath("$.[1].name").value("Historia"));
    }
}
