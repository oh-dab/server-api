package com.ohdab.classroom.controller.mapper;

import com.ohdab.classroom.controller.request.ClassroomReq;
import com.ohdab.classroom.controller.response.ClassroomRes;
import com.ohdab.classroom.service.dto.ClassroomReqDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomMapper {

    public static ClassroomReqDto classroomReqToClassroomReqDto(ClassroomReq classroomReq) {

        return ClassroomReqDto.builder()
                .name(classroomReq.getName())
                .description(classroomReq.getDescription())
                .grade(classroomReq.getGrade())
                .teacherId((long) classroomReq.getTeacherId())
                .build();
    }

    public static ClassroomRes createClassRoomRes() {
        return ClassroomRes.builder().message("반이 추가되었습니다.").build();
    }
}
