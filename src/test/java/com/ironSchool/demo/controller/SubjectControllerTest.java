package com.ironSchool.demo.controller;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService; // ✅ Usamos MockBean en lugar de @Mock

    private Subject sampleSubject;

    @BeforeEach
    public void setup() {
        sampleSubject = new Subject();
        sampleSubject.setId(1L);
        sampleSubject.setName("Matemáticas");
    }

    @Test
    public void testGetAllSubjects_authenticated_withRole() throws Exception {
        when(subjectService.getAllSubjects()).thenReturn(Arrays.asList(sampleSubject));

        mockMvc.perform(get("/api/subjects")
                        .with(SecurityMockMvcRequestPostProcessors.user("juan@example.com").roles("STUDENT")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetSubjectById() throws Exception {
        when(subjectService.getSubjectById(1L)).thenReturn(sampleSubject);

        mockMvc.perform(get("/api/subjects/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("juan@example.com").roles("STUDENT")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Matemáticas"));
    }

    @Test
    public void testCreateSubject() throws Exception {
        Subject requestSubject = new Subject();
        requestSubject.setName("Historia");

        when(subjectService.saveSubject(any(Subject.class))).thenReturn(sampleSubject);

        mockMvc.perform(post("/api/subjects")
                        .with(SecurityMockMvcRequestPostProcessors.user("carlos@example.com").roles("TEACHER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Historia\"}"))
                .andExpect(status().isCreated()) // ✅ Cambiado a Created si corresponde
                .andExpect(jsonPath("$.name").value("Matemáticas")); // ✅ Ahora es correcto
    }

    @Test
    public void testUpdateSubject() throws Exception {
        Subject updatedSubject = new Subject();
        updatedSubject.setName("Ciencias");

        when(subjectService.updateSubject(eq(1L), any(Subject.class))).thenReturn(updatedSubject);

        mockMvc.perform(put("/api/subjects/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("carlos@example.com").roles("TEACHER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Ciencias\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ciencias")); // ✅ Ahora coincide
    }

    @Test
    public void testDeleteSubject() throws Exception {
        doNothing().when(subjectService).deleteSubject(1L); // Simula un borrado exitoso

        mockMvc.perform(delete("/api/subjects/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("carlos@example.com").roles("TEACHER")))
                .andExpect(status().isNoContent()); // ✅ Asegura que devuelva 204
    }
}
