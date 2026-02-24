package com.edutech.progressive.service;

import java.util.List;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;

public interface StudentService {
    List<Student> getAllStudents() throws Exception;
    Integer addStudent(Student student) throws Exception;
    List<Student> getAllStudentSortedByName() throws Exception;

    default void emptyArrayList() {}
    default void updateStudent(Student student) throws Exception {}
    default void deleteStudent(int studentId) throws Exception {}
    default Student getStudentById(int studentId) throws Exception { return null; }

    default void modifyStudentDetails(StudentDTO studentDTO) {}
}