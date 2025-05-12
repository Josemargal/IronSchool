package com.ironSchool.demo.service;

import com.ironSchool.demo.model.UserAdmin;
import com.ironSchool.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAdmin saveUser(UserAdmin user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserAdmin findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(UserAdmin user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
