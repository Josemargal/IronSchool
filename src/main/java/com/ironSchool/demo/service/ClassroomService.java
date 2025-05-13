package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Optional<Classroom> getClassroomById(Long id) {
        return classroomRepository.findById(id);
    }

    public Classroom saveClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public Classroom updateClassroom(Long id, Classroom updatedClassroom) {
        if (!classroomRepository.existsById(id)) return null;

        updatedClassroom.setId(id);
        return classroomRepository.save(updatedClassroom);
    }

    public boolean deleteClassroom(Long id) {
        if (!classroomRepository.existsById(id)) return false;

        classroomRepository.deleteById(id);
        return true;
    }
}
