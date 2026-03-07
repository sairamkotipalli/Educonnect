package com.edutech.progressive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

    // Optional FK columns (helpful for queries/joins and to keep schema compatibility)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "teacher_id")
    private Integer teacherId;

    // ---- Associations (required for nested queries like findByTeacher_TeacherId) ----

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    @JsonIgnore
    private Teacher teacher;

    // ---- Constructors ----
    public User() {}

    public User(int userId, String username, String password, String role, Student student, Teacher teacher) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.student = student;
        this.teacher = teacher;
        this.studentId = (student != null ? student.getStudentId() : null);
        this.teacherId = (teacher != null ? teacher.getTeacherId() : null);
    }

    // ---- Getters ----
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public Integer getStudentId() { return studentId; }
    public Integer getTeacherId() { return teacherId; }
    public Student getStudent() { return student; }
    public Teacher getTeacher() { return teacher; }

    // ---- Setters ----
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }

    public void setStudent(Student student) {
        this.student = student;
        // keep FK column in sync if needed
        this.studentId = (student != null ? student.getStudentId() : null);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        // keep FK column in sync if needed
        this.teacherId = (teacher != null ? teacher.getTeacherId() : null);
    }
}