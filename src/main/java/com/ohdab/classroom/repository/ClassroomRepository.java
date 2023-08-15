package com.ohdab.classroom.repository;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.member.domain.student.studentid.StudentId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByTeacherId(long teacherId);

    List<StudentId> findStudentsById(long classroomId);
}
