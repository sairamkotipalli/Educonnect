package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImplJpa implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImplJpa(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }

    @Override
    public Integer addTeacher(Teacher teacher) throws Exception {
        Teacher saved = teacherRepository.save(teacher);
        return saved.getTeacherId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getTeacherSortedByExperience() throws Exception {
        List<Teacher> list = teacherRepository.findAll();
        list.sort(Comparator.comparingInt(Teacher::getYearsOfExperience));
        return list;
    }

    @Override
    public void updateTeacher(Teacher teacher) throws Exception {
        if (teacher.getTeacherId() == 0) {
            throw new IllegalArgumentException("teacherId must be provided for update");
        }
        if (!teacherRepository.existsById(teacher.getTeacherId())) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacher.getTeacherId());
        }
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) throws Exception {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacherId);
        }
        teacherRepository.deleteById(teacherId);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getTeacherById(int teacherId) throws Exception {
        return teacherRepository.findById(teacherId).orElse(null);
    }
}