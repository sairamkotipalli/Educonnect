package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.exception.CourseAlreadyExistsException;
import com.edutech.progressive.exception.CourseNotFoundException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImplJpa implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;

    public CourseServiceImplJpa(CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository,
                                AttendanceRepository attendanceRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() throws Exception {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(int courseId) throws Exception {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
    }

    @Override
    public Integer addCourse(Course course) throws Exception {
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name is required");
        }
        if (courseRepository.findByCourseName(course.getCourseName()) != null) {
            throw new CourseAlreadyExistsException("Course already exists with name: " + course.getCourseName());
        }
        Course saved = courseRepository.save(course);
        return saved.getCourseId();
    }

    @Override
    public void updateCourse(Course course) throws Exception {
        Course existing = courseRepository.findById(course.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + course.getCourseId()));
        Course byName = courseRepository.findByCourseName(course.getCourseName());
        if (byName != null && byName.getCourseId() != existing.getCourseId()) {
            throw new CourseAlreadyExistsException("Another course already exists with name: " + course.getCourseName());
        }
        existing.setCourseName(course.getCourseName());
        existing.setDescription(course.getDescription());
        existing.setTeacherId(course.getTeacherId());
        courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(int courseId) throws Exception {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }
        attendanceRepository.deleteByCourse_CourseId(courseId);
        enrollmentRepository.deleteByCourseId(courseId);
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }
}