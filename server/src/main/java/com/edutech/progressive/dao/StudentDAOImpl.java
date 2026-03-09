package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.dao.StudentDAO;
import com.edutech.progressive.entity.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (full_name, date_of_birth, contact_number, email, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getFullName());
            if (student.getDateOfBirth() != null) {
                ps.setDate(2, new java.sql.Date(student.getDateOfBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, student.getContactNumber());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getAddress());
            int updated = ps.executeUpdate();
            if (updated == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            try (PreparedStatement ps2 = con.prepareStatement("SELECT student_id FROM student ORDER BY student_id DESC LIMIT 1");
                 ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) return rs2.getInt(1);
            }
            return -1;
        }
    }

    @Override
    public Student getStudentById(int studentId) throws SQLException {
        String sql = "SELECT student_id, full_name, date_of_birth, contact_number, email, address FROM student WHERE student_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    java.sql.Date dobSql = rs.getDate("date_of_birth");
                    s.setDateOfBirth(dobSql != null ? new java.util.Date(dobSql.getTime()) : null);
                    s.setFullName(rs.getString("full_name"));
                    s.setContactNumber(rs.getString("contact_number"));
                    s.setEmail(rs.getString("email"));
                    s.setAddress(rs.getString("address"));
                    return s;
                }
                return null;
            }
        }
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET full_name = ?, date_of_birth = ?, contact_number = ?, email = ?, address = ? WHERE student_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getFullName());
            if (student.getDateOfBirth() != null) {
                ps.setDate(2, new java.sql.Date(student.getDateOfBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, student.getContactNumber());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getAddress());
            ps.setInt(6, student.getStudentId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT student_id, full_name, date_of_birth, contact_number, email, address FROM student";
        List<Student> list = new ArrayList<>();
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                java.sql.Date dobSql = rs.getDate("date_of_birth");
                s.setDateOfBirth(dobSql != null ? new java.util.Date(dobSql.getTime()) : null);
                s.setFullName(rs.getString("full_name"));
                s.setContactNumber(rs.getString("contact_number"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        }
        return list;
    }
}