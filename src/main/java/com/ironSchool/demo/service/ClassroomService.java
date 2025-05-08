package com.ironSchool.demo.service;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository repo;

    public ClassroomService(ClassroomRepository repo) {
        this.repo = repo;
    }

    public List<Classroom> findAll() {
        return repo.findAll();
    }

    public Classroom findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Classroom not found"));
    }

    public Classroom save(Classroom classroom) {
        return repo.save(classroom);
    }

    public Classroom update(Long id, Classroom updatedClassroom) {
        Classroom classroom = findById(id);
        classroom.setRoomNumber(updatedClassroom.getRoomNumber());
        classroom.setCapacity(updatedClassroom.getCapacity());
        return repo.save(classroom);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
