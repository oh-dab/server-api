package com.ohdab.classroom.controller;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;

import com.ohdab.classroom.controller.mapper.ClassroomMapper;
import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.request.AddStudentReq;
import com.ohdab.classroom.controller.request.AddWorkbookReq;
import com.ohdab.classroom.controller.request.UpdateClassroomReq;
import com.ohdab.classroom.controller.request.UpdateWorkbookInfoReq;
import com.ohdab.classroom.controller.response.*;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.dto.ClassroomWorkbookDto;
import com.ohdab.classroom.service.dto.ClassroomWorkbookUpdateDto;
import com.ohdab.classroom.service.usecase.*;
import com.ohdab.member.controller.response.DeleteStudentRes;
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
    private final FindClassroomDetailUsecase findClassroomDetailUsecase;
    private final UpdateClassroomInfoUsecase updateClassroomInfoUsecase;
    private final DeleteClassroomUsecase deleteClassroomUsecase;
    private final DeleteStudentUsecase deleteStudentUsecase;
    private final GetWorkbookListUsecase getWorkbookListUsecase;
    private final AddWorkbookUsecase addWorkbookUsecase;
    private final UpdateWorkbookInfoUsecase updateWorkbookInfoUsecase;
    private final AddStudentUsecase addStudentUsecase;

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

    @GetMapping("/{classroom-id}")
    public ResponseEntity<ClassroomDetailRes> getClassroomDetailById(
            @PathVariable("classroom-id") long id) {
        ClassroomDetailDtoResponse classroomDetail =
                findClassroomDetailUsecase.getClassroomDetailById(id);
        ClassroomDetailRes classroomDetailRes =
                ClassroomMapper.ClassroomDetailToClassroomDetailRes(classroomDetail);
        return ResponseEntity.ok(classroomDetailRes);
    }

    @PatchMapping("/info/{classroom-id}")
    public ResponseEntity<UpdateClassroomRes> updateClassroom(
            @PathVariable(name = "classroom-id") long classroomId,
            @RequestBody @Valid UpdateClassroomReq request) {
        ClassroomUpdateDtoRequest classroomUpdateDto =
                ClassroomMapper.classroomUpdateDtoReqToClassroomUpdateDtoRequest(
                        classroomId, request);
        updateClassroomInfoUsecase.updateClassroomInfo(classroomUpdateDto);
        return ResponseEntity.ok(UpdateClassroomRes.builder().message("반 정보 수정 성공").build());
    }

    @DeleteMapping("/expulsion/{classroom-id}")
    public ResponseEntity<DeleteClassroomRes> deleteClassroom(
            @PathVariable(name = "classroom-id") long classroomId) {
        deleteClassroomUsecase.deleteClassroomById(classroomId);
        return ResponseEntity.ok(DeleteClassroomRes.builder().message("반 삭제 성공").build());
    }

    @PatchMapping("/{classroom-id}/expulsion/students/{student-id}")
    public ResponseEntity<DeleteStudentRes> deleteStudent(
            @PathVariable("classroom-id") long classroomId,
            @PathVariable("student-id") long studentId) {
        deleteStudentUsecase.deleteStudent(classroomId, studentId);
        return ResponseEntity.ok(DeleteStudentRes.builder().message("학생을 삭제하였습니다.").build());
    }

    @GetMapping("/{classroom-id}/workbooks")
    public ResponseEntity<ClassroomWorkbookListRes> getWorkbookListByClassroomId(
            @PathVariable(name = "classroom-id") long classroomId) {
        List<ClassroomWorkbookDto.Response> classroomWorkbookDtoList =
                getWorkbookListUsecase.getWorkbookListByClassroomId(classroomId);
        return ResponseEntity.ok(
                ClassroomMapper.classroomWorkbookDtoListToResponseList(classroomWorkbookDtoList));
    }

    @PostMapping("/{classroom-id}/workbooks")
    public ResponseEntity<AddWorkbookRes> getWorkbookListByClassroomId(
            @PathVariable(name = "classroom-id") long classroomId,
            @RequestBody @Valid AddWorkbookReq addWorkbookReq) {
        ClassroomAddWorkbookDto.Request addWorkbookDto =
                ClassroomMapper.addWorkbookRequestToDto(addWorkbookReq);
        addWorkbookUsecase.addWorkbookByClassroomId(classroomId, addWorkbookDto);
        return ResponseEntity.ok(
                AddWorkbookRes.builder().message("해당 반에 교재 및 오답노트가 추가되었습니다.").build());
    }

    @PatchMapping("/workbooks/info/{workbook-id}")
    public ResponseEntity<UpdateWorkbookInfoRes> updateWorkInfobook(
            @PathVariable(name = "workbook-id") long workbookId,
            UpdateWorkbookInfoReq updateWorkbookInfoReq) {
        ClassroomWorkbookUpdateDto.Request workbookUpdateDtoReq =
                ClassroomMapper.updateWorkbookRequestToDto(workbookId, updateWorkbookInfoReq);
        updateWorkbookInfoUsecase.updateWorkbookInfo(workbookUpdateDtoReq);
        return ResponseEntity.ok(
                UpdateWorkbookInfoRes.builder().message("교재 정보가 수정 되었습니다.").build());
    }

    @PostMapping("/{classroom-id}/students/enrollment")
    public ResponseEntity<AddStudentRes> addStudent(
            @PathVariable(name = "classroom-id") long classroomId,
            @RequestBody @Valid AddStudentReq addStudentReq) {
        addStudentUsecase.addStudent(
                ClassroomMapper.toAddStudentDtoRequest(classroomId, addStudentReq));
        return ResponseEntity.ok(AddStudentRes.builder().message("해당 반에 학생이 추가되었습니다.").build());
    }
}
