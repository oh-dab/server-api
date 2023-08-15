package com.ohdab.classroom.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FindAllClassroomByTeacherIdRepositoryTest {

    @Autowired private ClassroomRepository classroomRepository;
    @Autowired private EntityManager em;

    @Test
    void 선생님_아이디로_반목록_조회() {
        // given
        Classroom classroom1 =
                Classroom.builder()
                        .teacher(TeacherId.builder().id(1L).build())
                        .classroomInfo(
                                ClassroomInfo.builder()
                                        .name("1반")
                                        .description("1반 설명")
                                        .grade(Grade.HIGH_1)
                                        .build())
                        .build();
        classroomRepository.save(classroom1);

        Classroom classroom2 =
                Classroom.builder()
                        .teacher(TeacherId.builder().id(1L).build())
                        .classroomInfo(
                                ClassroomInfo.builder()
                                        .name("2반")
                                        .description("2반 설명")
                                        .grade(Grade.HIGH_1)
                                        .build())
                        .build();
        classroomRepository.save(classroom2);

        // when
        List<Classroom> foundClassrooms = classroomRepository.findAllByTeacherId(1L);

        // then
        assertThat(foundClassrooms.get(0).getTeacher().getId()).isEqualTo(1L);
        assertThat(foundClassrooms.get(0).getClassroomInfo().getName()).isEqualTo("1반");
        assertThat(foundClassrooms.get(0).getClassroomInfo().getDescription()).isEqualTo("1반 설명");
        assertThat(foundClassrooms.get(0).getClassroomInfo().getGrade()).isEqualTo(Grade.HIGH_1);

        assertThat(foundClassrooms.get(1).getTeacher().getId()).isEqualTo(1L);
        assertThat(foundClassrooms.get(1).getClassroomInfo().getName()).isEqualTo("2반");
        assertThat(foundClassrooms.get(1).getClassroomInfo().getDescription()).isEqualTo("2반 설명");
        assertThat(foundClassrooms.get(1).getClassroomInfo().getGrade()).isEqualTo(Grade.HIGH_1);
    }
}
