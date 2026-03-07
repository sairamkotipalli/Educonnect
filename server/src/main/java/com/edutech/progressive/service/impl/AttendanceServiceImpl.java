package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Attendance;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 CourseRepository courseRepository,
                                 StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance createAttendance(Attendance attendance) {
        if (attendance.getCourse() == null || attendance.getStudent() == null || attendance.getAttendanceDate() == null) {
            throw new RuntimeException("Course, Student and attendanceDate are required");
        }
        int courseId = attendance.getCourse().getCourseId();
        int studentId = attendance.getStudent().getStudentId();
        Date date = attendance.getAttendanceDate();

        if (attendanceRepository.findByCourse_CourseIdAndStudent_StudentIdAndAttendanceDate(courseId, studentId, date).isPresent()) {
            throw new RuntimeException("Attendance already exists for this student, course and date");
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Invalid course"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Invalid student"));

        attendance.setCourse(course);
        attendance.setStudent(student);

        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(int attendanceId) {
        if (!attendanceRepository.existsById(attendanceId)) {
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> getAttendanceByStudent(int studentId) {
        return attendanceRepository.findByStudent_StudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> getAttendanceByCourse(int courseId) {
        return attendanceRepository.findByCourse_CourseId(courseId);
    }
}
