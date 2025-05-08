package com.ironSchool.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserAdmin {

    private int grade;

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;
}
