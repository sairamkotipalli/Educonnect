package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Student;
import com.edutech.progressive.exception.StudentAlreadyExistsException;
import com.edutech.progressive.service.impl.StudentServiceImplArraylist;
import com.edutech.progressive.service.impl.StudentServiceImplJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImplJpa studentServiceJpa;
    private final StudentServiceImplArraylist studentServiceArrayList;

    public StudentController(StudentServiceImplJpa studentServiceJpa,
                             StudentServiceImplArraylist studentServiceArrayList) {
        this.studentServiceJpa = studentServiceJpa;
        this.studentServiceArrayList = studentServiceArrayList;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            return ResponseEntity.ok(studentServiceJpa.getAllStudents());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable int studentId) {
        try {
            Student s = studentServiceJpa.getStudentById(studentId);
            return ResponseEntity.ok(s);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addStudent(@RequestBody Student student) {
        try {
            Integer id = studentServiceJpa.addStudent(student);
            return ResponseEntity.created(URI.create("/student/" + id)).body(id);
        } catch (StudentAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable int studentId, @RequestBody Student student) {
        try {
            student.setStudentId(studentId);
            studentServiceJpa.updateStudent(student);
            return ResponseEntity.ok().build();
        } catch (StudentAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int studentId) {
        try {
            studentServiceJpa.deleteStudent(studentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Student>> getAllStudentFromArrayList() {
        return ResponseEntity.ok(studentServiceArrayList.getAllStudents());
    }

    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addStudentToArrayList(@RequestBody Student student) {
        Integer size = studentServiceArrayList.addStudent(student);
        return ResponseEntity.status(201).body(size);
    }

    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Student>> getAllStudentSortedByNameFromArrayList() {
        return ResponseEntity.ok(studentServiceArrayList.getAllStudentSortedByName());
    }
}