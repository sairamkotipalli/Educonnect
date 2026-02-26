package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.StudentService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentServiceImplArraylist implements StudentService {

    private static List<Student> studentList = new ArrayList<>();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        studentList.add(new Student(1, "Alice Johnson", parseDate(sdf, "2003-05-12"), "9876543210", "alice@example.com", "12, Park Street"));
        studentList.add(new Student(2, "Bob Anderson", parseDate(sdf, "2002-11-23"), "9123456780", "bob@example.com", "45, Lake View"));
        studentList.add(new Student(3, "Charlie Brown", parseDate(sdf, "2004-01-05"), "9012345678", "charlie@example.com", "78, Hill Road"));
    }

    private static Date parseDate(SimpleDateFormat sdf, String value) {
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentList);
    }

    @Override
    public Integer addStudent(Student student) {
        if (student == null) return studentList.size();
        studentList.add(student);
        return studentList.size();
    }

    @Override
    public List<Student> getAllStudentSortedByName() {
        List<Student> copy = new ArrayList<>(studentList);
        Collections.sort(copy); 
        return copy;
    }

    @Override
    public void emptyArrayList() {
        studentList = new ArrayList<>();
    }
    
    @Override
    public void updateStudent(Student student) {
        if (student == null) return;
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId() == student.getStudentId()) {
                studentList.set(i, student);
                return;
            }
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        studentList.removeIf(s -> s.getStudentId() == studentId);
    }

    @Override
    public Student getStudentById(int studentId) {
        for (Student s : studentList) {
            if (s.getStudentId() == studentId) return s;
        }
        return null;
    }
}