
package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.TeacherDTO;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImplJpa implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImplJpa(TeacherRepository teacherRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Teacher> getAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }

    @Override
    public Integer addTeacher(Teacher teacher) throws Exception {
        if (teacher.getEmail() != null) {
            Teacher exists = teacherRepository.findByEmail(teacher.getEmail());
            if (exists != null) {
                throw new TeacherAlreadyExistsException("Teacher already exists with email: " + teacher.getEmail());
            }
        }
        Teacher saved = teacherRepository.save(teacher);
        return saved.getTeacherId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Teacher> getTeacherSortedByExperience() throws Exception {
        List<Teacher> list = teacherRepository.findAll();
        list.sort(Comparator.comparingInt(Teacher::getYearsOfExperience));
        return list;
    }

    @Override
    public void updateTeacher(Teacher teacher) throws Exception {
        if (!teacherRepository.existsById(teacher.getTeacherId())) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacher.getTeacherId());
        }
        if (teacher.getEmail() != null) {
            Teacher exists = teacherRepository.findByEmail(teacher.getEmail());
            if (exists != null && exists.getTeacherId() != teacher.getTeacherId()) {
                throw new TeacherAlreadyExistsException("Another teacher already exists with email: " + teacher.getEmail());
            }
        }
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) throws Exception {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacherId);
        }
        enrollmentRepository.deleteByTeacherId(teacherId);    // as per your schema; keep if correct
        courseRepository.deleteByTeacherId(teacherId);        // idem
        userRepository.deleteByTeacher_TeacherId(teacherId);  // <-- changed
        teacherRepository.deleteById(teacherId);
    }

    public void modifyTeacherDetails(TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(teacherDTO.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + teacherDTO.getTeacherId()));

        if (teacherDTO.getEmail() != null) {
            Teacher exists = teacherRepository.findByEmail(teacherDTO.getEmail());
            if (exists != null && exists.getTeacherId() != teacherDTO.getTeacherId()) {
                throw new RuntimeException("Another teacher already exists with email: " + teacherDTO.getEmail());
            }
        }

        teacher.setFullName(teacherDTO.getFullName());
        teacher.setContactNumber(teacherDTO.getContactNumber());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setYearsOfExperience(teacherDTO.getYearsOfExperience() == null ? 0 : teacherDTO.getYearsOfExperience());
        teacherRepository.save(teacher);

        User user = userRepository.findByTeacher_TeacherId(teacherDTO.getTeacherId()); // <-- changed
        if (user != null) {
            if (teacherDTO.getUsername() != null) {
                User byUsername = userRepository.findByUsername(teacherDTO.getUsername());
                if (byUsername != null && byUsername.getUserId() != user.getUserId()) {
                    throw new RuntimeException("Username already exists: " + teacherDTO.getUsername());
                }
                user.setUsername(teacherDTO.getUsername());
            }
            if (teacherDTO.getPassword() != null && !teacherDTO.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
            }
            userRepository.save(user);
        }
    }


}

