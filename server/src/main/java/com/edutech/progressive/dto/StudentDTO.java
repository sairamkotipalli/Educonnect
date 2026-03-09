
package com.edutech.progressive.dto;

import java.util.Date;

public class StudentDTO {
    private int studentId;
    private String username;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;
    private String address;

    public StudentDTO() {}

    public int getStudentId() { return studentId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
