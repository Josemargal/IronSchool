package com.ironSchool.demo.config;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private final String userEmail = "test@example.com";

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        // Inyectar los valores necesarios para las pruebas
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "testSecretKeyNeedsToBeAtLeast32CharactersLong");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 3600000L);

        // Crear UserDetails simulado
        userDetails = new User(userEmail, "password", new ArrayList<>());
    }

    @Test
    public void testGenerateToken() {
        // Ejecutar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Verificar
        assertNotNull(token);
        assertTrue(token.length() > 20); // Un token JWT válido debería ser más largo
    }

    @Test
    public void testExtractEmail() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Ejecutar
        String extractedEmail = jwtUtil.extractEmail(token);

        // Verificar
        assertEquals(userEmail, extractedEmail);
    }

    @Test
    public void testExtractRole() {
        // Preparar
        String role = "TEACHER";
        String token = jwtUtil.generateToken(userDetails, role);

        // Ejecutar
        String extractedRole = jwtUtil.extractRole(token);

        // Verificar
        assertEquals(role, extractedRole);
    }

    @Test
    public void testValidateToken_ValidToken() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Ejecutar
        boolean isValid = jwtUtil.validateToken(token, userDetails);

        // Verificar
        assertTrue(isValid);
    }

    @Test
    public void testIsTokenExpired_NotExpired() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Ejecutar
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Verificar
        assertFalse(isExpired);
    }

    @Test
    public void testExtractExpiration() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Ejecutar
        Date expiration = jwtUtil.extractExpiration(token);

        // Verificar
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date())); // La fecha de expiración debería ser futura
    }

    @Test
    public void testExtractAllClaims() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        // Ejecutar
        Claims claims = jwtUtil.extractClaim(token, claims1 -> claims1);

        // Verificar
        assertNotNull(claims);
        assertEquals(userEmail, claims.getSubject());
        assertEquals("STUDENT", claims.get("role"));
    }

    @Test
    public void testValidateToken_InvalidUsername() {
        // Preparar
        String token = jwtUtil.generateToken(userDetails, "STUDENT");
        UserDetails differentUser = new User("different@example.com", "password", new ArrayList<>());

        // Ejecutar
        boolean isValid = jwtUtil.validateToken(token, differentUser);

        // Verificar
        assertFalse(isValid);
    }
}
