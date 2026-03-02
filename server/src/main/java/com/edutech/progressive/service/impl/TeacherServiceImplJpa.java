package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.repository.CourseRepository;
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
    private final CourseRepository courseRepository;

    public TeacherServiceImplJpa(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }

    @Override
    public Integer addTeacher(Teacher teacher) throws Exception {
        if (teacher.getEmail() != null) {
            Teacher exists = teacherRepository.findByEmail(teacher.getEmail());
            if (exists != null) {
                throw new TeacherAlreadyExistsException("Teacher already exists with email: " + teacher.getEmail());
            }
        }
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
        if (!teacherRepository.existsById(teacher.getTeacherId())) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacher.getTeacherId());
        }
        if (teacher.getEmail() != null) {
            Teacher exists = teacherRepository.findByEmail(teacher.getEmail());
            if (exists != null && exists.getTeacherId() != teacher.getTeacherId()) {
                throw new TeacherAlreadyExistsException("Another teacher already exists with email: " + teacher.getEmail());
            }
        }
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) throws Exception {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacherId);
        }
        courseRepository.deleteByTeacherId(teacherId);
        teacherRepository.deleteById(teacherId);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getTeacherById(int teacherId) throws Exception {
        return teacherRepository.findById(teacherId).orElse(null);
    }
}