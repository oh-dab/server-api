package com.ohdab.classroom.controller.mapper;

import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.response.AddClassroomRes;
import com.ohdab.classroom.service.dto.ClassroomDto;
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
}
