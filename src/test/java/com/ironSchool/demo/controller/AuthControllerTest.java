package com.ironSchool.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironSchool.demo.dto.AuthRequest;
import com.ironSchool.demo.dto.RegisterRequest;
import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.model.Teacher;
import com.ironSchool.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRegisterUser_student() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setName("Ana López");
        request.setEmail("ana@example.com");
        request.setPassword("123456");
        request.setUserType("STUDENT");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado exitosamente. ID: "));
    }

    @Test
    public void testLoginUser_success() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("ana@example.com");
        request.setPassword("123456");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testLoginUser_invalidCredentials() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("invalid@example.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isUnauthorized());
    }
}
