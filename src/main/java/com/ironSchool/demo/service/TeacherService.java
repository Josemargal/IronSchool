package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Teacher;
import com.ironSchool.demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }

    public List<Teacher> findAll() {
        return repo.findAll();
    }

    public Teacher findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public Teacher save(Teacher teacher) {
        return repo.save(teacher);
    }

    public Teacher update(Long id, Teacher updatedTeacher) {
        Teacher teacher = findById(id);
        teacher.setFullName(updatedTeacher.getFullName());
        teacher.setEmail(updatedTeacher.getEmail());
        teacher.setPassword(updatedTeacher.getPassword());
        teacher.setRole(updatedTeacher.getRole());
        teacher.setDepartment(updatedTeacher.getDepartment());
        return repo.save(teacher);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
