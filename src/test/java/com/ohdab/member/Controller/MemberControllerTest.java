package com.ohdab.member.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdab.member.controller.MemberController;
import com.ohdab.member.controller.request.AddTeacherReq;
import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.JoinRes;
import com.ohdab.member.controller.response.LoginRes;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.dto.MemberDtoForLogin;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import com.ohdab.member.service.usecase.JoinUsecase;
import com.ohdab.member.service.usecase.LoginUsecase;
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
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private JoinUsecase joinUsecase;
    @MockBean private LoginUsecase loginUsecase;
    @MockBean private GetTeacherListUsecase getTeacherListUsecase;
    @MockBean private AddTeacherUsecase addTeacherUsecase;

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
        final MemberDtoForLogin.Response loginResDto =
                MemberDtoForLogin.Response.builder().memberId(1L).jwtToken("jwt-token").build();

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

    @Test
    @WithMockUser
    void 선생님_목록_조회() throws Exception {
        // given
        final String url = "/members/teachers";

        List<MemberDtoForGetTeacherList.Response> responseList = new ArrayList<>();

        responseList.add(createTeacher(1L, "선생님"));
        responseList.add(createTeacher(2L, "선생님2"));
        responseList.add(createTeacher(3L, "선생님3"));

        // when
        when(getTeacherListUsecase.getTeacherList()).thenReturn(responseList);

        // then
        mockMvc.perform(get(url).with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.teachers[0].id").value(1),
                        jsonPath("$.teachers[0].name").value("선생님"),
                        jsonPath("$.teachers[0].authorities[0]").value("TEACHER"),
                        jsonPath("$.teachers[0].status").value("ACTIVE"),
                        jsonPath("$.teachers[1].id").value(2),
                        jsonPath("$.teachers[1].name").value("선생님2"),
                        jsonPath("$.teachers[1].authorities[0]").value("TEACHER"),
                        jsonPath("$.teachers[1].status").value("ACTIVE"),
                        jsonPath("$.teachers[2].id").value(3),
                        jsonPath("$.teachers[2].name").value("선생님3"),
                        jsonPath("$.teachers[2].authorities[0]").value("TEACHER"),
                        jsonPath("$.teachers[2].status").value("ACTIVE"))
                .andDo(print())
                .andDo(createDocument("members/teachers"));
    }

    private MemberDtoForGetTeacherList.Response createTeacher(long id, String name) {
        return MemberDtoForGetTeacherList.Response.builder()
                .id(id)
                .name(name)
                .authorities(List.of("TEACHER"))
                .status("ACTIVE")
                .build();
    }

    @Test
    @WithMockUser
    void 선생님_추가() throws Exception {
        // given
        final String url = "/members/teachers/enrollment";
        final AddTeacherReq addTeacherReq = AddTeacherReq.builder().name("선생님").build();

        // when

        // then
        mockMvc.perform(
                        post(url)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(addTeacherReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("선생님 추가에 성공하였습니다."))
                .andDo(print())
                .andDo(createDocument("members/teachers/enrollment"));
    }

    private RestDocumentationResultHandler createDocument(String identifier) {
        return document(
                identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }
}
