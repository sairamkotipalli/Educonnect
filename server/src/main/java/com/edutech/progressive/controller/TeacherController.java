package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.service.impl.TeacherServiceImplJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherServiceImplJpa teacherServiceImplJpa;

    public TeacherController(TeacherServiceImplJpa teacherServiceImplJpa) {
        this.teacherServiceImplJpa = teacherServiceImplJpa;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() throws Exception {
        return ResponseEntity.ok(teacherServiceImplJpa.getAllTeachers());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) throws Exception {
        Teacher t = teacherServiceImplJpa.getTeacherById(teacherId);
        if (t == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(t);
    }

    @PostMapping
    public ResponseEntity<Integer> addTeacher(@RequestBody Teacher teacher) throws Exception {
        Integer id = teacherServiceImplJpa.addTeacher(teacher);
        return ResponseEntity.status(201).body(id);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable int teacherId, @RequestBody Teacher teacher) throws Exception {
        teacher.setTeacherId(teacherId);
        teacherServiceImplJpa.updateTeacher(teacher);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) throws Exception {
        teacherServiceImplJpa.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/yearsofexperience")
    public ResponseEntity<List<Teacher>> getTeacherSortedByYearsOfExperience() throws Exception {
        return ResponseEntity.ok(teacherServiceImplJpa.getTeacherSortedByExperience());
    }
}