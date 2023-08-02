package com.ohdab.classroom.controller;

import com.ohdab.classroom.controller.mapper.ClassroomMapper;
import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.response.AddClassroomRes;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.usecase.AddClassroomUsecase;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {

    private final AddClassroomUsecase addClassroomUsecase;

    @PostMapping("/enrollment")
    public ResponseEntity<AddClassroomRes> addClassroom(
            @Valid @RequestBody AddClassroomReq addClassroomReq) {
        ClassroomDto.Request classroomReqDto =
                ClassroomMapper.classroomReqToClassroomDtoRequest(addClassroomReq);
        addClassroomUsecase.addClassroom(classroomReqDto);
        return ResponseEntity.ok(ClassroomMapper.createClassRoomRes());
    }
}
