package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.service.impl.TeacherServiceImplJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherServiceImplJpa teacherServiceImplJpa;

    public TeacherController(TeacherServiceImplJpa teacherServiceImplJpa) {
        this.teacherServiceImplJpa = teacherServiceImplJpa;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getAllTeachers());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) {
        try {
            Teacher t = teacherServiceImplJpa.getTeacherById(teacherId);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addTeacher(@RequestBody Teacher teacher) {
        try {
            Integer id = teacherServiceImplJpa.addTeacher(teacher);
            return ResponseEntity.created(URI.create("/teacher/" + id)).body(id);
        } catch (TeacherAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable int teacherId, @RequestBody Teacher teacher) {
        try {
            teacher.setTeacherId(teacherId);
            teacherServiceImplJpa.updateTeacher(teacher);
            return ResponseEntity.ok().build();
        } catch (TeacherAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) {
        try {
            teacherServiceImplJpa.deleteTeacher(teacherId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/yearsofexperience")
    public ResponseEntity<List<Teacher>> getTeacherSortedByYearsOfExperience() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getTeacherSortedByExperience());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}