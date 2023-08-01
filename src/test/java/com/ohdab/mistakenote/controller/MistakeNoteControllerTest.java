package com.ohdab.mistakenote.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudent;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudent.Response.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
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
@WebMvcTest(controllers = MistakeNoteController.class)
class MistakeNoteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;
    @MockBean private SaveMistakeNoteInfoUsecase saveMistakeNoteInfoUsecase;

    @Test
    @WithMockUser
    void 학생별_오답노트_조회() throws Exception {
        // given
        final String GET_MISTAKE_NOTE_INFO_BY_STUDENT_URL =
                "/mistake-notes/workbooks/{workbook-id}/students/{student-id}";

        final List<MistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        mistakeNoteInfo.add(MistakeNoteInfoDto.builder().wrongNumber(1).wrongCount(3).build());
        mistakeNoteInfo.add(MistakeNoteInfoDto.builder().wrongNumber(2).wrongCount(1).build());
        final GetMistakeNoteInfoOfStudent.Response responseDto =
                GetMistakeNoteInfoOfStudent.Response.builder()
                        .mistakeNoteInfo(mistakeNoteInfo)
                        .build();

        // when
        when(getMistakeNoteInfoUsecase.getMistakeNoteInfoOfStudent(anyLong(), anyLong()))
                .thenReturn(responseDto);

        // then
        mockMvc.perform(
                        get(GET_MISTAKE_NOTE_INFO_BY_STUDENT_URL, 1, 2)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].wrongNumber")
                                .value(responseDto.getMistakeNoteInfo().get(0).getWrongNumber()),
                        jsonPath("$[0].wrongCount")
                                .value(responseDto.getMistakeNoteInfo().get(0).getWrongCount()),
                        jsonPath("$[1].wrongNumber")
                                .value(responseDto.getMistakeNoteInfo().get(1).getWrongNumber()),
                        jsonPath("$[1].wrongCount")
                                .value(responseDto.getMistakeNoteInfo().get(1).getWrongCount()))
                .andDo(print())
                .andDo(createDocument("mistake_note/getMistakeNoteInfoByStudent"));
    }

    @Test
    @WithMockUser
    void 오답_기록하기() throws Exception {
        // given
        final String SAVE_MISTAKE_NOTE_INFO_URL =
                "/mistake-notes/workbooks/{workbook-id}/students/{student-id}";

        final int[] mistakeNumbers = {1, 2, 3, 4, 5};
        final SaveMistakeNoteInfoReq saveMistakeNoteInfoReq =
                SaveMistakeNoteInfoReq.builder().mistakeNumbers(mistakeNumbers).build();

        // when
        doNothing()
                .when(saveMistakeNoteInfoUsecase)
                .saveMistakeNoteInfo(any(SaveMistakeNoteInfoDto.class));

        // then
        mockMvc.perform(
                        post(SAVE_MISTAKE_NOTE_INFO_URL, 1, 2)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(saveMistakeNoteInfoReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("오답이 기록되었습니다."))
                .andDo(print())
                .andDo(createDocument("mistake_note/saveMistakeNoteInfo"));
    }

    @Test
    @WithMockUser
    void 교재_상세조회() throws Exception {
        // given
        final String GET_MISTAKE_NOTE_INFO_OF_CLASSROOM_URL =
                "/mistake-notes/workbooks/{workbook-id}";

        final List<StudentInfoDto> students = new ArrayList<>();
        students.add(StudentInfoDto.builder().studentId(2).name("갑").build());
        students.add(StudentInfoDto.builder().studentId(3).name("을").build());
        students.add(StudentInfoDto.builder().studentId(4).name("병").build());
        final List<AllMistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        mistakeNoteInfo.add(
                AllMistakeNoteInfoDto.builder().wrongNumber(1).wrongStudentsCount(4).build());
        mistakeNoteInfo.add(
                AllMistakeNoteInfoDto.builder().wrongNumber(4).wrongStudentsCount(2).build());
        mistakeNoteInfo.add(
                AllMistakeNoteInfoDto.builder().wrongNumber(10).wrongStudentsCount(7).build());
        final GetAllMistakeNoteInfoDto.Response responseDto =
                GetAllMistakeNoteInfoDto.Response.builder()
                        .students(students)
                        .allMistakeNoteInfo(mistakeNoteInfo)
                        .build();

        // when
        when(getMistakeNoteInfoUsecase.getAllMistakeNoteInfo(anyLong())).thenReturn(responseDto);

        // then
        mockMvc.perform(
                        get(GET_MISTAKE_NOTE_INFO_OF_CLASSROOM_URL, 1)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.students.[0].studentId").value(2),
                        jsonPath("$.students.[0].name").value("갑"),
                        jsonPath("$.students.[1].studentId").value(3),
                        jsonPath("$.students.[1].name").value("을"),
                        jsonPath("$.students.[2].studentId").value(4),
                        jsonPath("$.students.[2].name").value("병"),
                        jsonPath("$.mistakeNoteInfo.[0].wrongNumber").value(1),
                        jsonPath("$.mistakeNoteInfo.[0].wrongStudentsCount").value(4),
                        jsonPath("$.mistakeNoteInfo.[1].wrongNumber").value(4),
                        jsonPath("$.mistakeNoteInfo.[1].wrongStudentsCount").value(2),
                        jsonPath("$.mistakeNoteInfo.[2].wrongNumber").value(10),
                        jsonPath("$.mistakeNoteInfo.[2].wrongStudentsCount").value(7))
                .andDo(print())
                .andDo(createDocument("mistake_note/getAllMistakeNoteInfo"));
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
