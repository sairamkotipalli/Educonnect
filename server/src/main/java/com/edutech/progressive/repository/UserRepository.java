package com.edutech.progressive.repository;

import com.edutech.progressive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    // Use nested property resolution for associations:
    User findByTeacher_TeacherId(int teacherId);
    User findByStudent_StudentId(int studentId);

    void deleteByTeacher_TeacherId(int teacherId);
    void deleteByStudent_StudentId(int studentId);
}