package com.edutech.progressive.dto;

public class TeacherDTO {

    private int teacherId;
    private String username;
    private String password;
    private String fullName;
    private String contactNumber;
    private String email;
    private String subject;
    private Integer yearsOfExperience;

    public TeacherDTO() {}

    public int getTeacherId() { return teacherId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }

    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}