package com.ohdab.classroom.controller;

import com.ohdab.classroom.controller.mapper.ClassroomMapper;
import com.ohdab.classroom.controller.request.ClassroomReq;
import com.ohdab.classroom.controller.response.ClassroomRes;
import com.ohdab.classroom.service.dto.ClassroomReqDto;
import com.ohdab.classroom.service.usecase.ClassroomUsecase;
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

    private final ClassroomUsecase classroomUsecase;

    @PostMapping("/enrollment")
    public ResponseEntity<ClassroomRes> addClassroom(
            @Valid @RequestBody ClassroomReq classroomReq) {
        ClassroomReqDto classroomReqDto =
                ClassroomMapper.classroomReqToClassroomReqDto(classroomReq);
        classroomUsecase.addClassroom(classroomReqDto);
        return ResponseEntity.ok(ClassroomMapper.createClassRoomRes());
    }
}
