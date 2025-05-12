package com.ironSchool.demo.config;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        Student student = new Student();
        student.setEmail("juan@example.com");
        student.setPassword("123456");

        // Guarda el usuario en memoria (simulado)
        userDetails = userDetailsService.loadUserByUsername(student.getEmail());
    }

    @Test
    public void testGenerateToken_returnsValidToken() {
        String token = jwtUtil.generateToken(userDetails, "STUDENT");

        assertThat(token).isNotBlank();
        assertThat(jwtUtil.extractEmail(token)).isEqualTo("juan@example.com");
        assertThat(jwtUtil.extractRole(token)).isEqualTo("STUDENT");
        assertThat(jwtUtil.validateToken(token, userDetails)).isTrue();
    }

    @Test
    public void testValidateToken_withInvalidToken_returnsFalse() {
        String invalidToken = Jwts.builder()
                .setSubject("wronguser@example.com")
                .setExpiration(new Date(System.currentTimeMillis() + 1000))
                .signWith(getSecureKey(), SignatureAlgorithm.HS512)
                .compact();

        assertThat(jwtUtil.validateToken(invalidToken, userDetails)).isFalse();
    }

    @Test
    public void testIsTokenExpired_returnsFalse_forValidToken() {
        String token = jwtUtil.generateToken(userDetails, "STUDENT");
        assertThat(jwtUtil.isTokenExpired(token)).isFalse();
    }

    @Test
    public void testIsTokenExpired_returnsTrue_forExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject("juan@example.com")
                .setExpiration(new Date(0)) // Fecha pasada
                .signWith(getSecureKey(), SignatureAlgorithm.HS512)
                .compact();

        assertThat(jwtUtil.isTokenExpired(expiredToken)).isTrue();
    }

    private Key getSecureKey() {
        String secretString = "mySuperSecretKeyForJWTtokensThatNoOneCanGuess123!";
        return Keys.hmacShaKeyFor(secretString.getBytes());
    }
}
