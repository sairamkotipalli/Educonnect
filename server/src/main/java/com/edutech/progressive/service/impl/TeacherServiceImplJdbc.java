package com.edutech.progressive.service.impl;

import com.edutech.progressive.dao.TeacherDAO;
import com.edutech.progressive.dao.TeacherDAOImpl;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.service.TeacherService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherServiceImplJdbc implements TeacherService {

    private TeacherDAO teacherDAO;

    public TeacherServiceImplJdbc() {
        this.teacherDAO = new TeacherDAOImpl();
    }

    public TeacherServiceImplJdbc(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public List<Teacher> getAllTeachers() throws Exception {
        try {
            return teacherDAO.getAllTeachers();
        } catch (SQLException e) {
            throw new Exception("Failed to fetch teachers", e);
        }
    }

    @Override
    public Integer addTeacher(Teacher teacher) throws Exception {
        try {
            int id = teacherDAO.addTeacher(teacher);
            teacher.setTeacherId(id);
            return id;
        } catch (SQLException e) {
            throw new Exception("Failed to add teacher", e);
        }
    }

    @Override
    public List<Teacher> getTeacherSortedByExperience() throws Exception {
        try {
            return teacherDAO.getAllTeachers()
                    .stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new Exception("Failed to sort teachers", e);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) throws Exception {
        try {
            teacherDAO.updateTeacher(teacher);
        } catch (SQLException e) {
            throw new Exception("Failed to update teacher", e);
        }
    }

    @Override
    public void deleteTeacher(int teacherId) throws Exception {
        try {
            teacherDAO.deleteTeacher(teacherId);
        } catch (SQLException e) {
            throw new Exception("Failed to delete teacher", e);
        }
    }

    @Override
    public Teacher getTeacherById(int teacherId) throws Exception {
        try {
            return teacherDAO.getTeacherById(teacherId);
        } catch (SQLException e) {
            throw new Exception("Failed to fetch teacher", e);
        }
    }
}