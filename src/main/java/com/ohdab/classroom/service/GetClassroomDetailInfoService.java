package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static com.ohdab.classroom.service.helper.ClassroomHelperService.findExistingClassroom;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomDetailDto;
import com.ohdab.classroom.service.usecase.GetClassroomDetailInfoUsecase;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.repository.mapper.MemberMapper;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import com.ohdab.workbook.repository.mapper.WorkbookMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetClassroomDetailInfoService implements GetClassroomDetailInfoUsecase {

    private final ClassroomRepository classroomRepository;
    private final MemberMapper memberMapper;
    private final WorkbookMapper workbookMapper;

    @Override
    public ClassroomDetailDtoResponse getClassroomDetailById(long classroomId) {
        Classroom classroom = findExistingClassroom(classroomId, classroomRepository);
        List<ClassroomDetailDto.StudentInfoDto> students =
                memberMapper.findAllStudentForClassroomInfo(
                        getStudentIdList(classroom.getStudents()));
        List<ClassroomDetailDto.WorkbookInfoDto> workbooks =
                workbookMapper.findAllWorkbookForClassroomInfo(
                        getWorkbookIdList(classroom.getWorkbooks()));
        return mapToClassroomDetailDtoResponse(classroom, students, workbooks);
    }

    private List<Long> getStudentIdList(List<StudentId> students) {
        return students.stream().map(StudentId::getId).collect(Collectors.toList());
    }

    private List<Long> getWorkbookIdList(List<WorkbookId> workbookIds) {
        return workbookIds.stream().map(WorkbookId::getId).collect(Collectors.toList());
    }

    private ClassroomDetailDtoResponse mapToClassroomDetailDtoResponse(
            Classroom classroom,
            List<ClassroomDetailDto.StudentInfoDto> students,
            List<ClassroomDetailDto.WorkbookInfoDto> workbooks) {
        return ClassroomDetailDtoResponse.builder()
                .classroomId(classroom.getId())
                .teacherId(classroom.getTeacher().getId())
                .info(mapToClassRoomDetailInfo(classroom))
                .studentInfoDtoList(students)
                .workbookInfoDtoList(workbooks)
                .build();
    }

    private ClassroomDetailDto.ClassroomDetailInfo mapToClassRoomDetailInfo(Classroom classroom) {
        return ClassroomDetailDto.ClassroomDetailInfo.builder()
                .name(classroom.getClassroomInfo().getName())
                .description(classroom.getClassroomInfo().getDescription())
                .grade(classroom.getClassroomInfo().getGrade().toString())
                .build();
    }
}
