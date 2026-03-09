package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudent_StudentId(int studentId);

    List<Attendance> findByCourse_CourseId(int courseId);

    Optional<Attendance> findByCourse_CourseIdAndStudent_StudentIdAndAttendanceDate(int courseId, int studentId, Date attendanceDate);

    @Modifying
    void deleteByCourse_CourseId(int courseId);

    @Modifying
    void deleteByStudent_StudentId(int studentId);
}