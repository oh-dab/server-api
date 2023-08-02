package com.ohdab.classroom.service.mapper;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomServiceMapper {

    public static List<ClassroomDto.Response> classroomListToClassroomDtoList(
            List<Classroom> classrooms) {
        return classrooms.stream()
                .map(ClassroomServiceMapper::classroomToClassroomDto)
                .collect(Collectors.toList());
    }

    public static ClassroomDto.Response classroomToClassroomDto(Classroom classroom) {

        return ClassroomDto.Response.builder()
                .id(classroom.getId())
                .teacherId(classroom.getTeacher().getId())
                .info(
                        ClassroomDto.Info.builder()
                                .name(classroom.getClassroomInfo().getName())
                                .description(classroom.getClassroomInfo().getDescription())
                                .grade(classroom.getClassroomInfo().getGrade().getLabel())
                                .build())
                .studentIds(
                        classroom.getStudents().stream()
                                .map(StudentId::getId)
                                .collect(Collectors.toList()))
                .workbookIds(
                        classroom.getWorkbooks().stream()
                                .map(WorkbookId::getId)
                                .collect(Collectors.toList()))
                .build();
    }
}
