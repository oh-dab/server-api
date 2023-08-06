package com.ohdab.classroom.repository;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UpdateClassroomRepositoryTest {

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
        classroomRepository.save(classroom);
        em.flush();
        em.clear();
        Classroom saveClassroom = classroomRepository.findAllByTeacherId(1).get(0);

        saveClassroom.setClassroomInfo(
                ClassroomInfo.builder().name("2반").description("22").grade(Grade.HIGH_2).build());
        classroomRepository.save(saveClassroom);
        em.flush();
        em.clear();

        // when
        Classroom foundClassrooom = classroomRepository.findClassroomById(saveClassroom.getId());

        // then
        Assertions.assertThat(foundClassrooom.getId()).isEqualTo(1);
        Assertions.assertThat(foundClassrooom.getClassroomInfo().getName()).isEqualTo("2반");
        Assertions.assertThat(foundClassrooom.getClassroomInfo().getDescription()).isEqualTo("22");
        Assertions.assertThat(foundClassrooom.getClassroomInfo().getGrade())
                .isEqualTo(Grade.HIGH_2);
    }
}
