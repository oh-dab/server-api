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
class AddClassroomRepositoryTest {

    @Autowired private ClassroomRepository classroomRepository;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("반 추가 (저장) 테스트 -> 선생님 아이디를 통해 조회")
    void 반_추가_테스트() {
        // given
        long id = 1L;
        TeacherId teacherId = TeacherId.builder().id(id).build();
        String name = "1반";
        String desc = "1반에 대한 설명입니다.";

        ClassroomInfo classroomInfo =
                ClassroomInfo.builder().name(name).description(desc).grade(Grade.HIGH_1).build();

        Classroom classroom =
                Classroom.builder().classroomInfo(classroomInfo).teacher(teacherId).build();

        // when
        classroomRepository.save(classroom);
        List<Classroom> foundClassrooms = classroomRepository.findAllByTeacherId(teacherId.getId());

        // then
        assertThat(foundClassrooms.get(0).getTeacher().getId()).isEqualTo(teacherId.getId());
        assertThat(foundClassrooms.get(0).getClassroomInfo().getName()).isEqualTo(name);
        assertThat(foundClassrooms.get(0).getClassroomInfo().getDescription()).isEqualTo(desc);
        assertThat(foundClassrooms.get(0).getClassroomInfo().getGrade()).isEqualTo(Grade.HIGH_1);
    }
}
