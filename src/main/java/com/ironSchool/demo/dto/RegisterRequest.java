package com.ironSchool.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String userType; // Puede ser "TEACHER" o "STUDENT"
}
