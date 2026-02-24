package com.edutech.progressive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher implements Comparable<Teacher> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "subject", length = 100)
    private String subject;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    public Teacher() {}

    public Teacher(int teacherId, String fullName, String subject, String contactNumber, String email, int yearsOfExperience) {
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.subject = subject;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getTeacherId() { return teacherId; }
    public String getFullName() { return fullName; }
    public String getSubject() { return subject; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    @Override
    public int compareTo(Teacher other) {
        return Integer.compare(this.yearsOfExperience, other == null ? 0 : other.yearsOfExperience);
    }
}