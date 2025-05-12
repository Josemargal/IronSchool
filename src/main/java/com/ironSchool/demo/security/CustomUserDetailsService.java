package com.ironSchool.demo.security;

import com.ironSchool.demo.model.Teacher;
import com.ironSchool.demo.repository.TeacherRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;

    public CustomUserDetailsService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByFullEmail(username)  // <- CAMBIO AQUÍ
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(teacher.getFullEmail())
                .password(teacher.getPassword())
                .roles("TEACHER")
                .build();
    }
}

