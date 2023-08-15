package com.ohdab.classroom.service.mapper;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoInfo;
import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomDetailServiceMapper {

    public static ClassroomDetailDtoResponse classroomToClassroomDetail(Classroom classroom) {
        return ClassroomDetailDtoResponse.builder()
                .classroomId(classroom.getId())
                .teacherId(classroom.getTeacher().getId())
                .info(
                        ClassroomDetailDtoInfo.builder()
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
