package com.edutech.progressive.dao;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.entity.Student;

public interface StudentDAO {
    int addStudent(Student student) throws SQLException;
    Student getStudentById(int studentId) throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int studentId) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
}

