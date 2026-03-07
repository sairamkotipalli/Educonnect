package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.StudentAlreadyExistsException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StudentServiceImplJpa implements StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImplJpa(StudentRepository studentRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 AttendanceRepository attendanceRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudents() throws Exception {
        return studentRepository.findAll();
    }

    @Override
    public Integer addStudent(Student student) throws Exception {
        if (student.getEmail() != null) {
            Student exists = studentRepository.findByEmail(student.getEmail());
            if (exists != null) {
                throw new StudentAlreadyExistsException("Student already exists with email: " + student.getEmail());
            }
        }
        Student saved = studentRepository.save(student);
        return saved.getStudentId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudentSortedByName() throws Exception {
        List<Student> list = studentRepository.findAll();
        list.sort(Comparator.comparing(
                s -> s.getFullName() == null ? "" : s.getFullName(),
                String.CASE_INSENSITIVE_ORDER
        ));
        return list;
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        if (!studentRepository.existsById(student.getStudentId())) {
            throw new IllegalArgumentException("Student not found with id: " + student.getStudentId());
        }
        if (student.getEmail() != null) {
            Student exists = studentRepository.findByEmail(student.getEmail());
            if (exists != null && exists.getStudentId() != student.getStudentId()) {
                throw new StudentAlreadyExistsException("Another student already exists with email: " + student.getEmail());
            }
        }
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int studentId) throws Exception {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
        // ✅ Fixed to use nested property path for association
        attendanceRepository.deleteByStudent_StudentId(studentId);
        enrollmentRepository.deleteByStudentId(studentId);         // keep per your Day 10 signature
        userRepository.deleteByStudent_StudentId(studentId);       // ✅ fixed to nested property
        studentRepository.deleteById(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Student getStudentById(int studentId) throws Exception {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public void modifyStudentDetails(StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentDTO.getStudentId()));

        if (studentDTO.getEmail() != null) {
            Student exists = studentRepository.findByEmail(studentDTO.getEmail());
            if (exists != null && exists.getStudentId() != studentDTO.getStudentId()) {
                throw new RuntimeException("Another student already exists with email: " + studentDTO.getEmail());
            }
        }

        student.setFullName(studentDTO.getFullName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setContactNumber(studentDTO.getContactNumber());
        student.setEmail(studentDTO.getEmail());
        student.setAddress(studentDTO.getAddress());
        studentRepository.save(student);

        // ✅ Use nested method on repository to fetch the associated User
        User user = userRepository.findByStudent_StudentId(studentDTO.getStudentId());
        if (user != null) {
            if (studentDTO.getUsername() != null) {
                User byUsername = userRepository.findByUsername(studentDTO.getUsername());
                if (byUsername != null && byUsername.getUserId() != user.getUserId()) {
                    throw new RuntimeException("Username already exists: " + studentDTO.getUsername());
                }
                user.setUsername(studentDTO.getUsername());
            }
            if (studentDTO.getPassword() != null && !studentDTO.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
            }
            userRepository.save(user);
        }
    }
}