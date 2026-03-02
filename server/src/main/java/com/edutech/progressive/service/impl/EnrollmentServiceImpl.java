package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Enrollment;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public int createEnrollment(Enrollment enrollment) {
        if (enrollment.getStudent() == null || enrollment.getCourse() == null) {
            throw new RuntimeException("Student and Course are required");
        }
        int studentId = enrollment.getStudent().getStudentId();
        int courseId = enrollment.getCourse().getCourseId();

        if (enrollmentRepository.findByStudent_StudentIdAndCourse_CourseId(studentId, courseId).isPresent()) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Invalid student"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Invalid course"));

        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(new Date());
        }

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return saved.getEnrollmentId();
    }

    @Override
    public void updateEnrollment(Enrollment updatedEnrollment) {
        Enrollment existing = enrollmentRepository.findById(updatedEnrollment.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (updatedEnrollment.getStudent() != null) {
            Student s = studentRepository.findById(updatedEnrollment.getStudent().getStudentId())
                    .orElseThrow(() -> new RuntimeException("Invalid student"));
            existing.setStudent(s);
        }

        if (updatedEnrollment.getCourse() != null) {
            Course c = courseRepository.findById(updatedEnrollment.getCourse().getCourseId())
                    .orElseThrow(() -> new RuntimeException("Invalid course"));
            existing.setCourse(c);
        }

        if (updatedEnrollment.getEnrollmentDate() != null) {
            existing.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        }

        enrollmentRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(int enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollmentsByStudent(int studentId) {
        return enrollmentRepository.findAllByStudent_StudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollmentsByCourse(int courseId) {
        return enrollmentRepository.findAllByCourse_CourseId(courseId);
    }
}