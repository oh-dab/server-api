package com.ohdab.classroom.controller.mapper;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;

import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.request.UpdateClassroomReq;
import com.ohdab.classroom.controller.response.AddClassroomRes;
import com.ohdab.classroom.controller.response.ClassroomDetailRes;
import com.ohdab.classroom.controller.response.ClassroomResList;
import com.ohdab.classroom.service.dto.ClassroomDto;
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

    public static ClassroomDetailRes ClassroomDetailToClassroomDetailRes(
            ClassroomDetailDtoResponse detailDto) {
        return ClassroomDetailRes.builder()
                .id(detailDto.getClassroomId())
                .teacherId(detailDto.getTeacherId())
                .name(detailDto.getInfo().getName())
                .description(detailDto.getInfo().getDescription())
                .grade(detailDto.getInfo().getGrade())
                .studentIds(detailDto.getStudentIds())
                .workbookIds(detailDto.getWorkbookIds())
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
}
