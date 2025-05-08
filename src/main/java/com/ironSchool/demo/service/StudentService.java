package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Student;
import com.ironSchool.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Student findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student save(Student student) {
        return repo.save(student);
    }

    public Student update(Long id, Student updatedStudent) {
        Student student = findById(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setPassword(updatedStudent.getPassword());
        student.setRole(updatedStudent.getRole());
        student.setGrade(updatedStudent.getGrade());
        return repo.save(student);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
