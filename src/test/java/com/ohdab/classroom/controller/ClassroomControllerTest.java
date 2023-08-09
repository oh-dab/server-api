package com.ohdab.classroom.controller;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoInfo;
import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.controller.request.UpdateClassroomReq;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.dto.ClassroomWorkbookDto;
import com.ohdab.classroom.service.usecase.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(controllers = ClassroomController.class)
class ClassroomControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private AddClassroomUsecase addClassroomUsecase;
    @MockBean private FindClassroomListUsecase findClassroomListUsecase;
    @MockBean private FindClassroomDetailUsecase findClassroomDetailUsecase;
    @MockBean private UpdateClassroomInfoUsecase updateClassroomInfoUsecase;
    @MockBean private DeleteClassroomUsecase deleteClassroomUsecase;
    @MockBean private DeleteStudentUsecase deleteStudentUsecase;
    @MockBean private GetWorkbookListUsecase getWorkbookListUsecase;

    @Test
    @WithMockUser
    void 반추가() throws Exception {
        // given
        final String url = "/classrooms/enrollment";
        final AddClassroomReq addClassroomReq =
                AddClassroomReq.builder()
                        .name("1반")
                        .description("1반 설명입니다.")
                        .grade("high1")
                        .teacherId(1)
                        .build();

        // when

        // then
        mockMvc.perform(
                        post(url)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(addClassroomReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("반이 추가되었습니다."))
                .andDo(print())
                .andDo(createDocument("classrooms/enrollment"));
    }

    @Test
    @WithMockUser
    void 선생님_아이디로_반목록_조회() throws Exception {
        // given
        final String url = "/classrooms";

        List<ClassroomDto.Response> responseList = new ArrayList<>();

        responseList.add(
                ClassroomDto.Response.builder()
                        .teacherId(1L)
                        .id(1)
                        .info(
                                ClassroomDto.Info.builder()
                                        .name("1")
                                        .description("111")
                                        .grade("high1")
                                        .build())
                        .build());
        responseList.add(
                ClassroomDto.Response.builder()
                        .teacherId(1L)
                        .id(2)
                        .info(
                                ClassroomDto.Info.builder()
                                        .name("2")
                                        .description("222")
                                        .grade("high2")
                                        .build())
                        .build());

        // when
        when(findClassroomListUsecase.findClassroomListByTeacherId(1L)).thenReturn(responseList);
        // then
        mockMvc.perform(get(url).with(csrf()).param("teacherId", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.classroomInfoList[0].id").value(1),
                        jsonPath("$.classroomInfoList[0].name").value(1),
                        jsonPath("$.classroomInfoList[1].id").value(2),
                        jsonPath("$.classroomInfoList[1].name").value(2),
                        jsonPath("$.classroomInfoList[1].description").value(222),
                        jsonPath("$.classroomInfoList[1].grade").value("high2"))
                .andDo(print())
                .andDo(createDocument("classrooms"));
    }

    @Test
    @WithMockUser
    void 반_상세조회() throws Exception {
        // given
        final String url = "/classrooms/";

        List<Long> studentIds = new ArrayList<>();
        studentIds.add(3L);

        List<Long> workbookIds = new ArrayList<>();
        workbookIds.add(4L);

        ClassroomDetailDtoResponse classroomDetailDtoResponse =
                ClassroomDetailDtoResponse.builder()
                        .classroomId(1)
                        .teacherId(2)
                        .info(
                                ClassroomDetailDtoInfo.builder()
                                        .name("1반")
                                        .description("1반 설명")
                                        .grade("high1")
                                        .build())
                        .studentIds(studentIds)
                        .workbookIds(workbookIds)
                        .build();

        // when
        when(findClassroomDetailUsecase.getClassroomDetailById(1L))
                .thenReturn(classroomDetailDtoResponse);

        // then
        mockMvc.perform(get(url + "{classroom-id}", 1).with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.teacherId").value(2),
                        jsonPath("$.name").value("1반"),
                        jsonPath("$.description").value("1반 설명"),
                        jsonPath("$.grade").value("high1"),
                        jsonPath("$.studentIds[0]").value(3),
                        jsonPath("$.workbookIds[0]").value(4))
                .andDo(print())
                .andDo(createDocument("classrooms/{classroom-id}"));
    }

    @Test
    @WithMockUser
    void 반정보수정() throws Exception {
        // given
        final String url = "/classrooms/info/";
        UpdateClassroomReq request =
                UpdateClassroomReq.builder().name("2반").description("2222").grade("high2").build();

        // when

        // then
        mockMvc.perform(
                        patch(url + "{classroom-id}", 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("반 정보 수정 성공"))
                .andDo(print())
                .andDo(createDocument("classrooms/info/{classroom-id}"));
    }

    @Test
    @WithMockUser
    void 반삭제() throws Exception {
        // given
        final String url = "/classrooms/expulsion/";

        // when

        // then
        mockMvc.perform(delete(url + "{classroom-id}", 1).with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("반 삭제 성공"))
                .andDo(print())
                .andDo(createDocument("classrooms/expulsion/{classroom-id}"));
    }

    @Test
    @WithMockUser
    void 학생_삭제() throws Exception {
        // given
        final String url = "/classrooms/{classroom-id}/expulsion/students/{student-id}";

        // when
        doNothing().when(deleteStudentUsecase).deleteStudent(anyLong(), anyLong());

        // then
        mockMvc.perform(patch(url, 1L, 2L).with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(createDocument("classrooms/deleteStudent"));
    }

    @Test
    @WithMockUser
    void 반_식별자로_교재_목록_조회() throws Exception {
        // given
        final String url = "/classrooms/{classroom-id}/workbooks";
        List<ClassroomWorkbookDto.Response> workbookDtoList = new ArrayList<>();
        ClassroomWorkbookDto.Response workbookDtoRes1 = createWorkbookDto(1L, "교재");
        ClassroomWorkbookDto.Response workbookDtoRes2 = createWorkbookDto(2L, "교재2");
        ClassroomWorkbookDto.Response workbookDtoRes3 = createWorkbookDto(3L, "교재3");
        workbookDtoList.add(workbookDtoRes1);
        workbookDtoList.add(workbookDtoRes2);
        workbookDtoList.add(workbookDtoRes3);

        // when
        when(getWorkbookListUsecase.getWorkbookListByClassroomId(Mockito.anyLong()))
                .thenReturn(workbookDtoList);

        // then
        mockMvc.perform(get(url, 1L).with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.workbooks[0].id").value(workbookDtoRes1.getId()),
                        jsonPath("$.workbooks[0].name").value(workbookDtoRes1.getName()),
                        jsonPath("$.workbooks[0].created_at")
                                .value(workbookDtoRes1.getCreatedAt().toLocalDate().toString()),
                        jsonPath("$.workbooks[1].id").value(workbookDtoRes2.getId()),
                        jsonPath("$.workbooks[1].name").value(workbookDtoRes2.getName()),
                        jsonPath("$.workbooks[1].created_at")
                                .value(workbookDtoRes2.getCreatedAt().toLocalDate().toString()),
                        jsonPath("$.workbooks[2].id").value(workbookDtoRes3.getId()),
                        jsonPath("$.workbooks[2].name").value(workbookDtoRes3.getName()),
                        jsonPath("$.workbooks[2].created_at")
                                .value(workbookDtoRes3.getCreatedAt().toLocalDate().toString()))
                .andDo(print())
                .andDo(createDocument("classrooms/{classroom-id}/workbooks"));
    }

    private ClassroomWorkbookDto.Response createWorkbookDto(long id, String name) {
        return ClassroomWorkbookDto.Response.builder()
                .id(id)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
