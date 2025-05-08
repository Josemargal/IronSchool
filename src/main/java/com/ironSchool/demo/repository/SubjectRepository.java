package com.ironSchool.demo.repository;

import com.ironSchool.demo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
