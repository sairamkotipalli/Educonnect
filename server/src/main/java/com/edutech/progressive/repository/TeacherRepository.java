package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.progressive.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByTeacherId(int teacherId);
    Teacher findByEmail(String email);
}
