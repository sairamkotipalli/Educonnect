package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Attendance;
import com.edutech.progressive.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        try {
            return ResponseEntity.ok(attendanceService.getAllAttendance());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        try {
            Attendance saved = attendanceService.createAttendance(attendance);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable int attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAllAttendanceByStudent(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAllAttendanceByCourse(@PathVariable int courseId) {
        try {
            return ResponseEntity.ok(attendanceService.getAttendanceByCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}