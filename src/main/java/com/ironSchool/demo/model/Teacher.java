package com.ironSchool.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends UserAdmin {

    private String email;
    private String fullName;
    private String department;


    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;
}


