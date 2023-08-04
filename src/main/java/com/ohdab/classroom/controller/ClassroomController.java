package com.ohdab.classroom.controller;

import com.ohdab.classroom.controller.mapper.ClassroomMapper;
import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.response.AddClassroomRes;
import com.ohdab.classroom.controller.response.ClassroomResList;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.usecase.AddClassroomUsecase;
import com.ohdab.classroom.service.usecase.FindClassroomListUsecase;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {

    private final AddClassroomUsecase addClassroomUsecase;
    private final FindClassroomListUsecase findClassroomListUsecase;

    @PostMapping("/enrollment")
    public ResponseEntity<AddClassroomRes> addClassroom(
            @Valid @RequestBody AddClassroomReq addClassroomReq) {
        ClassroomDto.Request classroomReqDto =
                ClassroomMapper.classroomReqToClassroomDtoRequest(addClassroomReq);
        addClassroomUsecase.addClassroom(classroomReqDto);
        return ResponseEntity.ok(ClassroomMapper.createClassRoomRes());
    }

    @GetMapping("")
    public ResponseEntity<ClassroomResList> getClassroomListByTeacherId(
            @RequestParam(name = "teacherId") long teacherId) {
        List<ClassroomDto.Response> responses =
                findClassroomListUsecase.findClassroomListByTeacherId(teacherId);
        return ResponseEntity.ok(ClassroomMapper.classroomDtoListToClassroomResList(responses));
    }
}
