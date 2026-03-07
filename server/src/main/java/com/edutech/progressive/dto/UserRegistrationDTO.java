package com.edutech.progressive.dto;

import java.sql.Date;

public class UserRegistrationDTO {
    private String username;            
    private String password;            
    private String role;                
    private String fullName;        
    private String contactNumber;      
    private String email;               

    private String address;           
    private Date dateOfBirth;         

    private String subject;            
    private Integer yearsOfExperience;  

    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String username, String password, String role,
                               String fullName, String contactNumber, String email,
                               String address, Date dateOfBirth,
                               String subject, Integer yearsOfExperience) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.subject = subject;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}
