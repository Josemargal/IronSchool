package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository repo;

    public SubjectService(SubjectRepository repo) {
        this.repo = repo;
    }

    public List<Subject> findAll() {
        return repo.findAll();
    }

    public Subject findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    public Subject save(Subject subject) {
        return repo.save(subject);
    }

    public Subject update(Long id, Subject updatedSubject) {
        Subject subject = findById(id);
        subject.setName(updatedSubject.getName());
       /* subject.setSchedule(updatedSubject.getSchedule());*/
        subject.setClassroom(updatedSubject.getClassroom());
        subject.setTeacher(updatedSubject.getTeacher());
        subject.setStudents(updatedSubject.getStudents());
        return repo.save(subject);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
