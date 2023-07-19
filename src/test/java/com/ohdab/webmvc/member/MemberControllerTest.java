package com.ohdab.webmvc.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.MemberController;
import com.ohdab.dto.LoginResDto;
import com.ohdab.port.in.JoinUsecase;
import com.ohdab.port.in.LoginUsecase;
import com.ohdab.request.JoinReq;
import com.ohdab.request.LoginReq;
import com.ohdab.response.JoinRes;
import com.ohdab.response.LoginRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private JoinUsecase joinUsecase;
    @MockBean private LoginUsecase loginUsecase;

    @Test
    @WithMockUser
    void 회원가입() throws Exception {
        // given
        final String JOIN_URL = "/members/join";
        final JoinReq joinReq =
                JoinReq.builder().name("홍길동").password("1234").role(List.of("STUDENT")).build();
        final JoinRes joinRes = JoinRes.builder().message("회원가입이 완료되었습니다.").build();

        // when

        // then
        mockMvc.perform(
                        post(JOIN_URL)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(joinReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(joinRes.getMessage()))
                .andDo(print())
                .andDo(createDocument("members/join"));
    }

    @Test
    @WithMockUser
    void 로그인() throws Exception {
        // given
        final String LOGIN_URL = "/members/login";
        final LoginReq loginReq = LoginReq.builder().name("홍길동").password("1234").build();
        final LoginRes loginRes =
                LoginRes.builder()
                        .memberId(1L)
                        .message("로그인에 성공하였습니다.")
                        .jwtToken("jwt-token")
                        .build();
        final LoginResDto loginResDto =
                LoginResDto.builder().memberId(1L).jwtToken("jwt-token").build();

        // when
        when(loginUsecase.login(any())).thenReturn(loginResDto);

        // then
        mockMvc.perform(
                        post(LOGIN_URL)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(loginRes.getMessage()),
                        jsonPath("$.memberId").value(loginRes.getMemberId()),
                        jsonPath("$.jwtToken").value(loginRes.getJwtToken()))
                .andDo(print())
                .andDo(createDocument("members/login"));
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
