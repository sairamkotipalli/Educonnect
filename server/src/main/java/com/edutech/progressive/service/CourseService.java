package com.edutech.progressive.service;

import java.util.List;

import com.edutech.progressive.entity.Course;

public interface CourseService {
    List<Course> getAllCourses() throws Exception;
    Course getCourseById(int courseId) throws Exception;
    Integer addCourse(Course course) throws Exception;
    void updateCourse(Course course) throws Exception;
    void deleteCourse(int courseId) throws Exception;

    default List<Course> getAllCourseByTeacherId(int teacherId) {
        return List.of();
    }
}