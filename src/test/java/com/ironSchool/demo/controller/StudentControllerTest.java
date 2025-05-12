package com.ironSchool.demo.controller;

import com.ironSchool.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testGetAllStudents_ReturnsOk() throws Exception {
        when(studentService.findAll()).thenReturn(List.of(new Student()));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }
}

