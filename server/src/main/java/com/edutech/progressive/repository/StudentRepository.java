package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentId(int studentId);
    Student findByEmail(String email);
}