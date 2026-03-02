package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.service.impl.CourseServiceImplJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseServiceImplJpa courseService;

    public CourseController(CourseServiceImplJpa courseService) {
        this.courseService = courseService;
    }

    @PostConstruct
    public void seedCourses() throws Exception {
        if (courseService.getAllCourses().size() == 0) {
            courseService.addCourse(new Course(0, "Mathematics Basics", "Introductory math course", 101));
            courseService.addCourse(new Course(0, "Physics Fundamentals", "Basic physics principles", 102));
            courseService.addCourse(new Course(0, "Chemistry Essentials", "Essential chemistry concepts", 103));
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() throws Exception {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable int courseId) throws Exception {
        Course c = courseService.getCourseById(courseId);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<Integer> addCourse(@RequestBody Course course) throws Exception {
        Integer id = courseService.addCourse(course);
        return ResponseEntity.created(URI.create("/course/" + id)).body(id);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable int courseId, @RequestBody Course course) throws Exception {
        course.setCourseId(courseId);
        courseService.updateCourse(course);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) throws Exception {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getAllCourseByTeacherId(@PathVariable int teacherId) {
        return ResponseEntity.ok(courseService.getAllCourseByTeacherId(teacherId));
    }
}