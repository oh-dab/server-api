package com.ohdab.classroom.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UpdateClassroomRepositoryTest {

    @Autowired private ClassroomRepository classroomRepository;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("반 수정 repo 테스트")
    void 반_수정_레포_테스트() {
        // given
        long id = 1L;
        TeacherId teacherId = TeacherId.builder().id(id).build();
        String name = "1반";
        String desc = "1반에 대한 설명입니다.";

        ClassroomInfo classroomInfo =
                ClassroomInfo.builder().name(name).description(desc).grade(Grade.HIGH_1).build();

        Classroom classroom =
                Classroom.builder().classroomInfo(classroomInfo).teacher(teacherId).build();
        Classroom savedClassroom = classroomRepository.save(classroom);

        // when
        savedClassroom.updateClassroomInfo(
                ClassroomInfo.builder().name("2반").description("22").grade(Grade.HIGH_2).build());
        em.flush();
        em.clear();

        // then
        assertThat(savedClassroom.getClassroomInfo().getName()).isEqualTo("2반");
        assertThat(savedClassroom.getClassroomInfo().getDescription()).isEqualTo("22");
        assertThat(savedClassroom.getClassroomInfo().getGrade()).isEqualTo(Grade.HIGH_2);
    }
}
