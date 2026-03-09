package com.edutech.progressive;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.repository.CourseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EduConnectApplication {
    public static void main(String[] args) {
        System.out.println("Welcome to EduConnect Progressive Project!");
        SpringApplication.run(EduConnectApplication.class, args);
    }

    @Bean
    public org.springframework.boot.CommandLineRunner loadCourses(CourseRepository courseRepository) {
        return args -> {
            if (courseRepository.count() == 0) {
                courseRepository.save(new Course(0, "Mathematics Basics", "Introductory math course", 101));
                courseRepository.save(new Course(0, "Physics Fundamentals", "Basic physics principles", 102));
                courseRepository.save(new Course(0, "Chemistry Essentials", "Essential chemistry concepts", 103));
            }
        };
    }
}