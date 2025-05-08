package com.ironSchool.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends UserAdmin {

    private String department;

    @OneToMany(mappedBy = "teacher")
    private Set<Subject> subjects;
}
