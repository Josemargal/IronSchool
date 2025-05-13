package com.ironSchool.demo.controller;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping
    public List<Classroom> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) {
        Optional<Classroom> classroom = classroomService.getClassroomById(id);
        return classroom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Classroom> createClassroom(@RequestBody Classroom classroom) {
        Classroom saved = classroomService.saveClassroom(classroom);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> updateClassroom(
            @PathVariable Long id,
            @RequestBody Classroom classroomDetails) {

        Classroom updated = classroomService.updateClassroom(id, classroomDetails);

        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        boolean deleted = classroomService.deleteClassroom(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
