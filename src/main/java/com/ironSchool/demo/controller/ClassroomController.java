package com.ironSchool.demo.controller;

import com.ironSchool.demo.model.Classroom;
import com.ironSchool.demo.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private final ClassroomService service;

    public ClassroomController(ClassroomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Classroom> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Classroom getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Classroom create(@RequestBody Classroom classroom) {
        return service.save(classroom);
    }

    @PutMapping("/{id}")
    public Classroom update(@PathVariable Long id, @RequestBody Classroom classroom) {
        return service.update(id, classroom);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
