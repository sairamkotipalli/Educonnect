package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable int studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Integer> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(-1);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable int studentId, @RequestBody Student student) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int studentId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Student>> getAllStudentFromArrayList() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PostMapping("/toArrayList")
    public ResponseEntity<Void> addStudentToArrayList() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Student>> getAllStudentSortedByNameFromArrayList() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}