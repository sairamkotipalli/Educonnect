package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudent_StudentIdAndCourse_CourseId(int studentId, int courseId);

    List<Enrollment> findAllByStudent_StudentId(int studentId);

    List<Enrollment> findAllByCourse_CourseId(int courseId);

    @Modifying
    @Query("delete from Enrollment e where e.course.courseId = ?1")
    void deleteByCourseId(int courseId);

    @Modifying
    @Query("delete from Enrollment e where e.student.studentId = ?1")
    void deleteByStudentId(int studentId);

    @Modifying
    @Query("delete from Enrollment e where e.course.teacherId = ?1")
    void deleteByTeacherId(int teacherId);
}