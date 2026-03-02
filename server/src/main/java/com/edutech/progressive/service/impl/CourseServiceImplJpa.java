package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class CourseServiceImplJpa implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImplJpa(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostConstruct
    public void loadSampleCourses() {
        if (courseRepository.count() == 0) {
            courseRepository.save(new Course(0, "Mathematics Basics", "Introductory math course", 101));
            courseRepository.save(new Course(0, "Physics Fundamentals", "Basic physics principles", 102));
            courseRepository.save(new Course(0, "Chemistry Essentials", "Essential chemistry concepts", 103));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() throws Exception {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(int courseId) throws Exception {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public Integer addCourse(Course course) throws Exception {
        Course saved = courseRepository.save(course);
        return saved.getCourseId();
    }

    @Override
    public void updateCourse(Course course) throws Exception {
        Course existing = courseRepository.findById(course.getCourseId()).orElse(null);
        if (existing == null) throw new IllegalArgumentException("Course not found");
        existing.setCourseName(course.getCourseName());
        existing.setDescription(course.getDescription());
        existing.setTeacherId(course.getTeacherId());
        courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(int courseId) throws Exception {
        if (!courseRepository.existsById(courseId)) throw new IllegalArgumentException("Course not found");
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }
}