package com.ohdab.classroom.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteClassroomRepositoryTest {

    @Autowired private ClassroomRepository classroomRepository;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("반 삭제 테스트")
    void 반삭제() {
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
        classroomRepository.delete(savedClassroom);
        em.flush();
        em.clear();

        List<Classroom> classrooms = classroomRepository.findAllByTeacherId(1);

        // then
        assertThat(classrooms).isEmpty();
    }
}
