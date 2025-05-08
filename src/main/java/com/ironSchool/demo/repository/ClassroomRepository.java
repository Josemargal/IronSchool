package com.ironSchool.demo.repository;

import com.ironSchool.demo.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
