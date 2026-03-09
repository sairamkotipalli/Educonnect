package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.dao.CourseDAO;
import com.edutech.progressive.entity.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public int addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO course (course_name, description, teacher_id) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            if (course.getTeacherId() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, course.getTeacherId());
            }
            int updated = ps.executeUpdate();
            if (updated == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            try (PreparedStatement ps2 = con.prepareStatement("SELECT course_id FROM course ORDER BY course_id DESC LIMIT 1");
                 ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) return rs2.getInt(1);
            }
            return -1;
        }
    }

    @Override
    public Course getCourseById(int courseId) throws SQLException {
        String sql = "SELECT course_id, course_name, description, teacher_id FROM course WHERE course_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getInt("course_id"));
                    c.setCourseName(rs.getString("course_name"));
                    c.setDescription(rs.getString("description"));
                    int tid = rs.getInt("teacher_id");
                    c.setTeacherId(rs.wasNull() ? 0 : tid);
                    return c;
                }
                return null;
            }
        }
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE course SET course_name = ?, description = ?, teacher_id = ? WHERE course_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            if (course.getTeacherId() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, course.getTeacherId());
            }
            ps.setInt(4, course.getCourseId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM course WHERE course_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        String sql = "SELECT course_id, course_name, description, teacher_id FROM course";
        List<Course> list = new ArrayList<>();
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setDescription(rs.getString("description"));
                int tid = rs.getInt("teacher_id");
                c.setTeacherId(rs.wasNull() ? 0 : tid);
                list.add(c);
            }
        }
        return list;
    }
}