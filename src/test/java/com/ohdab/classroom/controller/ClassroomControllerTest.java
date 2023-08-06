package com.ohdab.classroom.controller;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoInfo;
import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.classroom.controller.request.AddClassroomReq;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.usecase.AddClassroomUsecase;
import com.ohdab.classroom.service.usecase.FindClassroomDetailUsecase;
import com.ohdab.classroom.service.usecase.FindClassroomListUsecase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
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
                .andDo(createDocument("classrooms?teacherId="));
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

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
