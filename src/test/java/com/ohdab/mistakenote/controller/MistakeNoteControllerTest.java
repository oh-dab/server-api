package com.ohdab.mistakenote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(controllers = MistakeNoteController.class)
class MistakeNoteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;

    private final String GET_MISTAKE_NOTE_INFO_BY_STUDENT_URL = "/mistake-notes/workbooks/{workbook-id}/students/{student-id}";

    @Test
    @WithMockUser
    void 학생별_오답노트_조회() throws Exception {
        // given

        List<MistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        mistakeNoteInfo.add(
                MistakeNoteInfoDto.builder().wrongNumber(1).wrongCount(3).build());
        mistakeNoteInfo.add(
                MistakeNoteInfoDto.builder().wrongNumber(2).wrongCount(1).build());

        // when
        when(getMistakeNoteInfoUsecase.getMistakeNoteInfoByStudent(anyLong(), anyLong()))
                .thenReturn(mistakeNoteInfo);

        // then
        mockMvc.perform(
                        get(GET_MISTAKE_NOTE_INFO_BY_STUDENT_URL, 1, 2)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].wrongNumber")
                                .value(mistakeNoteInfo.get(0).getWrongNumber()),
                        jsonPath("$[0].wrongCount")
                                .value(mistakeNoteInfo.get(0).getWrongCount()),
                        jsonPath("$[1].wrongNumber")
                                .value(mistakeNoteInfo.get(1).getWrongNumber()),
                        jsonPath("$[1].wrongCount")
                                .value(mistakeNoteInfo.get(1).getWrongCount()))
                .andDo(print())
                .andDo(createDocument("mistake_note/getMistakeNoteInfoByStudent"));
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}