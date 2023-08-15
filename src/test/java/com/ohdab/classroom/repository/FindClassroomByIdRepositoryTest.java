package com.ohdab.classroom.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FindClassroomByIdRepositoryTest {

    @Autowired private ClassroomRepository classroomRepository;
    @Autowired private EntityManager em;

    @Test
    void 반_상세조회() {

        // given
        Classroom classroom =
                Classroom.builder()
                        .teacher(TeacherId.builder().id(2L).build())
                        .classroomInfo(
                                ClassroomInfo.builder()
                                        .name("1반")
                                        .description("1반 설명")
                                        .grade(Grade.HIGH_1)
                                        .build())
                        .build();
        classroom.addStudent(StudentId.builder().id(3).build());
        classroom.addWorkbook(WorkbookId.builder().id(4).build());
        classroomRepository.save(classroom);
        long id = classroom.getId();

        // when
        Classroom foundClassroom = classroomRepository.findById(id).get();

        // then
        assertThat(foundClassroom.getId()).isEqualTo(id);
        assertThat(foundClassroom.getTeacher().getId()).isEqualTo(2);
        assertThat(foundClassroom.getClassroomInfo().getName()).isEqualTo("1반");
        assertThat(foundClassroom.getClassroomInfo().getDescription()).isEqualTo("1반 설명");
        assertThat(foundClassroom.getClassroomInfo().getGrade()).isEqualTo(Grade.HIGH_1);
        assertThat(foundClassroom.getStudents().get(0).getId()).isEqualTo(3);
        assertThat(foundClassroom.getWorkbooks().get(0).getId()).isEqualTo(4);
    }
}
