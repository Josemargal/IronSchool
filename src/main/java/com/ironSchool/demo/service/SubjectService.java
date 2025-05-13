package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Subject;
import com.ironSchool.demo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        if (subject != null) {
            subject.setName(subjectDetails.getName());
            subject.setSchedule(subjectDetails.getSchedule());
            return subjectRepository.save(subject);
        }
        return null;
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
