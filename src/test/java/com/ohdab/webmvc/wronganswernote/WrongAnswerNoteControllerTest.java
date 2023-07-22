package com.ohdab.webmvc.wronganswernote;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.WrongAnswerNoteController;
import com.ohdab.dto.NoteInfoByStudentDto;
import com.ohdab.port.in.GetNoteInfoUsecase;
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
@WebMvcTest(controllers = WrongAnswerNoteController.class)
class WrongAnswerNoteControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private GetNoteInfoUsecase getNoteInfoUsecase;

    @Test
    @WithMockUser
    void 학생별_오답노트_조회() throws Exception {
        // given
        final String GET_NOTE_INFO_BY_STUDENT_URL =
                "/notes/workbooks/{workbook-id}/students/{student-id}";
        List<NoteInfoByStudentDto> noteInfoByStudentDtoList = new ArrayList<>();
        noteInfoByStudentDtoList.add(
                NoteInfoByStudentDto.builder().wrongNumber(1).wrongCount(3).build());
        noteInfoByStudentDtoList.add(
                NoteInfoByStudentDto.builder().wrongNumber(2).wrongCount(1).build());

        // when
        when(getNoteInfoUsecase.getNoteInfoByStudent(anyLong(), anyLong()))
                .thenReturn(noteInfoByStudentDtoList);

        // then
        mockMvc.perform(
                        get(GET_NOTE_INFO_BY_STUDENT_URL, 1, 2)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].wrongNumber")
                                .value(noteInfoByStudentDtoList.get(0).getWrongNumber()),
                        jsonPath("$[0].wrongCount")
                                .value(noteInfoByStudentDtoList.get(0).getWrongCount()),
                        jsonPath("$[1].wrongNumber")
                                .value(noteInfoByStudentDtoList.get(1).getWrongNumber()),
                        jsonPath("$[1].wrongCount")
                                .value(noteInfoByStudentDtoList.get(1).getWrongCount()))
                .andDo(print())
                .andDo(createDocument("wrong_answer_note/getNoteInfoByStudent"));
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
