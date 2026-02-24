package com.edutech.progressive.service;

import java.util.List;

import com.edutech.progressive.dto.TeacherDTO;
import com.edutech.progressive.entity.Teacher;

public interface TeacherService {
    List<Teacher> getAllTeachers() throws Exception;
    Integer addTeacher(Teacher teacher) throws Exception;
    List<Teacher> getTeacherSortedByExperience() throws Exception;

    default void emptyArrayList() {}
    default void updateTeacher(Teacher teacher) throws Exception {}
    default void deleteTeacher(int teacherId) throws Exception {}
    default Teacher getTeacherById(int teacherId) throws Exception { return null; }

    default void modifyTeacherDetails(TeacherDTO teacherDTO) {}
}