package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StudentServiceImplJpa implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImplJpa(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Integer addStudent(Student student) {
        Student saved = studentRepository.save(student);
        return saved.getStudentId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudentSortedByName() {
        List<Student> list = studentRepository.findAll();
        list.sort(Comparator.comparing(
                s -> s.getFullName() == null ? "" : s.getFullName(),
                String.CASE_INSENSITIVE_ORDER
        ));
        return list;
    }

    @Override
    public void updateStudent(Student student) {
        if (student.getStudentId() == 0) {
            throw new IllegalArgumentException("studentId must be provided for update");
        }
        if (!studentRepository.existsById(student.getStudentId())) {
            throw new IllegalArgumentException("Student not found with id: " + student.getStudentId());
        }
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public void modifyStudentDetails(StudentDTO studentDTO) {
    }
}