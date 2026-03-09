package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoginServiceImpl(UserRepository userRepository,
                                StudentRepository studentRepository,
                                TeacherRepository teacherRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegistrationDTO dto) throws Exception {
        if (dto.getUsername() == null || dto.getPassword() == null || dto.getRole() == null) {
            throw new IllegalArgumentException("username, password, and role are required");
        }
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new IllegalArgumentException("username already exists");
        }

        String role = dto.getRole().toUpperCase();
        Student s = null;
        Teacher t = null;

        if ("STUDENT".equals(role)) {
            s = new Student();
            s.setFullName(dto.getFullName());
            s.setDateOfBirth(dto.getDateOfBirth());
            s.setContactNumber(dto.getContactNumber());
            s.setEmail(dto.getEmail());
            s.setAddress(dto.getAddress());
            s = studentRepository.save(s);
        } else if ("TEACHER".equals(role)) {
            t = new Teacher();
            t.setFullName(dto.getFullName());
            t.setSubject(dto.getSubject());
            t.setContactNumber(dto.getContactNumber());
            t.setEmail(dto.getEmail());
            t.setYearsOfExperience(dto.getYearsOfExperience() == null ? 0 : dto.getYearsOfExperience());
            t = teacherRepository.save(t);
        } else {
            throw new IllegalArgumentException("Invalid role");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);
        user.setStudent(s);
        user.setTeacher(t);

        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserDetails(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(identifier);
        if (user == null) throw new UsernameNotFoundException("User not found");
        String role = user.getRole() == null ? "" : user.getRole().toUpperCase();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(() -> "ROLE_" + role)
        );
    }
}
