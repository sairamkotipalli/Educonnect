package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.service.TeacherService;

public class TeacherServiceImplArraylist implements TeacherService {

    private static List<Teacher> teacherList = new ArrayList<>();

    static {
        teacherList.add(new Teacher(101, "Dr. Priya Mehta", "Mathematics", "9998877766", "priya.mehta@school.com", 12));
        teacherList.add(new Teacher(102, "Mr. Rahul Verma", "Physics", "8887766554", "rahul.verma@school.com", 5));
        teacherList.add(new Teacher(103, "Ms. Neha Sharma", "Chemistry", "7776655443", "neha.sharma@school.com", 8));
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teacherList);
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        if (teacher == null) return teacherList.size();
        teacherList.add(teacher);
        return teacherList.size();
    }

    @Override
    public List<Teacher> getTeacherSortedByExperience() {
        List<Teacher> copy = new ArrayList<>(teacherList);
        Collections.sort(copy); 
        return copy;
    }

    @Override
    public void emptyArrayList() {
        teacherList = new ArrayList<>();
    }


    @Override
    public void updateTeacher(Teacher teacher) {
        if (teacher == null) return;
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getTeacherId() == teacher.getTeacherId()) {
                teacherList.set(i, teacher);
                return;
            }
        }
    }

    @Override
    public void deleteTeacher(int teacherId) {
        teacherList.removeIf(t -> t.getTeacherId() == teacherId);
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        for (Teacher t : teacherList) {
            if (t.getTeacherId() == teacherId) return t;
        }
        return null;
    }
}
