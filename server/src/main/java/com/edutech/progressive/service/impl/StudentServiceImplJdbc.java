package com.edutech.progressive.service.impl;

import com.edutech.progressive.dao.StudentDAO;
import com.edutech.progressive.dao.StudentDAOImpl;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.StudentService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImplJdbc implements StudentService {

    private StudentDAO studentDAO;

    public StudentServiceImplJdbc() {
        this.studentDAO = new StudentDAOImpl();
    }

    public StudentServiceImplJdbc(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            throw new Exception("Failed to fetch students", e);
        }
    }

    @Override
    public Integer addStudent(Student student) throws Exception {
        try {
            return studentDAO.addStudent(student);
        } catch (SQLException e) {
            throw new Exception("Failed to add student", e);
        }
    }

    @Override
    public List<Student> getAllStudentSortedByName() throws Exception {
        try {
            return studentDAO.getAllStudents()
                    .stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new Exception("Failed to sort students", e);
        }
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        try {
            studentDAO.updateStudent(student);
        } catch (SQLException e) {
            throw new Exception("Failed to update student", e);
        }
    }

    @Override
    public void deleteStudent(int studentId) throws Exception {
        try {
            studentDAO.deleteStudent(studentId);
        } catch (SQLException e) {
            throw new Exception("Failed to delete student", e);
        }
    }

    @Override
    public Student getStudentById(int studentId) throws Exception {
        try {
            return studentDAO.getStudentById(studentId);
        } catch (SQLException e) {
            throw new Exception("Failed to fetch student", e);
        }
    }
}