package com.ohdab.classroom.controller.mapper;

import com.ohdab.classroom.controller.request.ClassroomReq;
import com.ohdab.classroom.controller.response.ClassroomRes;
import com.ohdab.classroom.service.dto.ClassroomDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomMapper {

    public static ClassroomDto.Request classroomReqToClassroomDtoRequest(
            ClassroomReq classroomReq) {

        return ClassroomDto.Request.builder()
                .info(
                        ClassroomDto.Info.builder()
                                .name(classroomReq.getName())
                                .description(classroomReq.getDescription())
                                .grade(classroomReq.getGrade())
                                .build())
                .teacherId(classroomReq.getTeacherId())
                .build();
    }

    public static ClassroomRes createClassRoomRes() {
        return ClassroomRes.builder().message("반이 추가되었습니다.").build();
    }
}
