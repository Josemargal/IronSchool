package com.ironSchool.demo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DtoTests {

    @Test
    public void testAuthRequest() {
        // Crear objeto
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password123");

        // Verificar getters
        assertEquals("test@example.com", authRequest.getEmail());
        assertEquals("password123", authRequest.getPassword());
    }

    @Test
    public void testAuthResponse() {
        // Crear objeto
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken("jwt-token-123");
        authResponse.setUserType("STUDENT");

        // Verificar getters
        assertEquals("jwt-token-123", authResponse.getToken());
        assertEquals("STUDENT", authResponse.getUserType());
    }

    @Test
    public void testRegisterRequest() {
        // Crear objeto
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Nuevo Usuario");
        registerRequest.setEmail("nuevo@example.com");
        registerRequest.setPassword("newpass123");
        registerRequest.setUserType("TEACHER");

        // Verificar getters
        assertEquals("Nuevo Usuario", registerRequest.getName());
        assertEquals("nuevo@example.com", registerRequest.getEmail());
        assertEquals("newpass123", registerRequest.getPassword());
        assertEquals("TEACHER", registerRequest.getUserType());
    }
}
