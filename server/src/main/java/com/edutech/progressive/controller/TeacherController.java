package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Teacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Integer> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(-1);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable int teacherId, @RequestBody Teacher teacher) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/yearsofexperience")
    public ResponseEntity<List<Teacher>> getTeacherSortedByYearsOfExperience() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}