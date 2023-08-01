package com.ohdab.classroom.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.classroom.controller.request.ClassroomReq;
import com.ohdab.classroom.service.usecase.ClassroomUsecase;
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
    @MockBean private ClassroomUsecase classroomUsecase;

    @Test
    @WithMockUser
    void 반추가() throws Exception {
        // given
        final String url = "/classrooms/enrollment";
        final ClassroomReq classroomReq =
                ClassroomReq.builder()
                        .name("1반")
                        .description("1반 설명입니다.")
                        .grade("high1")
                        .teacherId(1)
                        .build();

        // when
        mockMvc.perform(
                        post(url)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(classroomReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("반이 추가되었습니다."))
                .andDo(print())
                .andDo(createDocument("classrooms/enrollment"));

        // then

    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
