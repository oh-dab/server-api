package com.ohdab.classroom.controller.mapper;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;

import com.ohdab.classroom.controller.request.*;
import com.ohdab.classroom.controller.response.AddClassroomRes;
import com.ohdab.classroom.controller.response.ClassroomDetailRes;
import com.ohdab.classroom.controller.response.ClassroomResList;
import com.ohdab.classroom.controller.response.ClassroomWorkbookListRes;
import com.ohdab.classroom.service.dto.AddStudentDto;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto.Request;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.dto.ClassroomWorkbookDto.Response;
import com.ohdab.classroom.service.dto.ClassroomWorkbookUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomMapper {
    public static ClassroomDto.Request classroomReqToClassroomDtoRequest(
            AddClassroomReq addClassroomReq) {

        return ClassroomDto.Request.builder()
                .info(
                        ClassroomDto.Info.builder()
                                .name(addClassroomReq.getName())
                                .description(addClassroomReq.getDescription())
                                .grade(addClassroomReq.getGrade())
                                .build())
                .teacherId(addClassroomReq.getTeacherId())
                .build();
    }

    public static AddClassroomRes createClassRoomRes() {
        return AddClassroomRes.builder().message("반이 추가되었습니다.").build();
    }

    public static ClassroomResList classroomDtoListToClassroomResList(
            List<ClassroomDto.Response> classroomDtoList) {
        return ClassroomResList.builder()
                .classroomInfoList(
                        classroomDtoList.stream()
                                .map(
                                        classroomDto ->
                                                ClassroomResList.ClassroomInfo.builder()
                                                        .id(classroomDto.getId())
                                                        .name(classroomDto.getInfo().getName())
                                                        .description(
                                                                classroomDto
                                                                        .getInfo()
                                                                        .getDescription())
                                                        .grade(classroomDto.getInfo().getGrade())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    public static ClassroomDetailRes classroomDetailToClassroomDetailRes(
            ClassroomDetailDtoResponse detailDto) {
        return ClassroomDetailRes.builder()
                .id(detailDto.getClassroomId())
                .teacherId(detailDto.getTeacherId())
                .name(detailDto.getInfo().getName())
                .description(detailDto.getInfo().getDescription())
                .grade(detailDto.getInfo().getGrade())
                .studentInfoList(
                        detailDto.getStudentInfoDtoList().stream()
                                .map(
                                        studentInfo ->
                                                ClassroomDetailRes.StudentInfo.builder()
                                                        .studentId(studentInfo.getStudentId())
                                                        .studentName(studentInfo.getStudentName())
                                                        .build())
                                .collect(Collectors.toList()))
                .workbookInfoList(
                        detailDto.getWorkbookInfoDtoList().stream()
                                .map(
                                        workbookInfo ->
                                                ClassroomDetailRes.WorkbookInfo.builder()
                                                        .workbookId(workbookInfo.getWorkbookId())
                                                        .workbookName(
                                                                workbookInfo.getWorkbookName())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    public static ClassroomUpdateDtoRequest classroomUpdateDtoReqToClassroomUpdateDtoRequest(
            long classroomId, UpdateClassroomReq request) {
        return ClassroomUpdateDtoRequest.builder()
                .classroomId(classroomId)
                .name(request.getName())
                .description(request.getDescription())
                .grade(request.getGrade())
                .build();
    }

    public static ClassroomWorkbookListRes classroomWorkbookDtoListToResponseList(
            List<Response> classroomWorkbookDtoList) {
        return ClassroomWorkbookListRes.builder()
                .workbookList(
                        classroomWorkbookDtoList.stream()
                                .map(
                                        w ->
                                                ClassroomWorkbookListRes.WorkbookInfo.builder()
                                                        .id(w.getId())
                                                        .name(w.getName())
                                                        .createdAt(w.getCreatedAt().toLocalDate())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    public static Request addWorkbookRequestToDto(AddWorkbookReq addWorkbookReq) {
        return ClassroomAddWorkbookDto.Request.builder()
                .name(addWorkbookReq.getName())
                .description(addWorkbookReq.getDescription())
                .startingNumber(addWorkbookReq.getStartingNumber())
                .endingNumber(addWorkbookReq.getEndingNumber())
                .build();
    }

    public static ClassroomWorkbookUpdateDto.Request updateWorkbookRequestToDto(
            long workbookId, UpdateWorkbookInfoReq updateWorkbookInfoReq) {
        return ClassroomWorkbookUpdateDto.Request.builder()
                .id(workbookId)
                .name(updateWorkbookInfoReq.getName())
                .description(updateWorkbookInfoReq.getDescription())
                .build();
    }

    public static AddStudentDto.Request toAddStudentDtoRequest(
            long classroomId, AddStudentReq addStudentReq) {
        return AddStudentDto.Request.builder()
                .classroomId(classroomId)
                .studentName(addStudentReq.getStudentName())
                .build();
    }
}
