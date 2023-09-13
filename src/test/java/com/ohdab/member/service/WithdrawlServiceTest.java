package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.MemberStatus;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.exception.AlreadyWithdrawlException;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.usecase.WithdrawlUsecase;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WithdrawlService.class})
class WithdrawlServiceTest {

    @Autowired private WithdrawlUsecase withdrawlUsecase;
    @MockBean private MemberRepository memberRepository;

    @DisplayName("withdrawl 메서드는")
    @Nested
    class withdrawlMethod {

        @DisplayName("해당 회원이 존재하고")
        @Nested
        class isExitsMember {

            @DisplayName("탈퇴하지 않았다면")
            @Nested
            class isActiveMember {

                @DisplayName("탈퇴처리한다.")
                @Test
                void withdrawl() {
                    // given
                    final long memberId = 1L;
                    final Member member =
                            Member.builder()
                                    .memberInfo(
                                            MemberInfo.builder().name("갑").password("1234").build())
                                    .authorities(
                                            List.of(
                                                    new Authority("STUDENT"),
                                                    new Authority("TEACHER")))
                                    .build();

                    // when
                    when(memberRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(member));
                    withdrawlUsecase.withdrawl(memberId);

                    // then
                    assertThat(member.getStatus()).isEqualTo(MemberStatus.INACTIVE);
                }
            }

            @DisplayName("이미 탈퇴했다면")
            @Nested
            class alreadyWithdrawl {

                @DisplayName("AlreadyWithdrawlException 예외를 던진다.")
                @Test
                void throwAlreadyWithdrawlException() {
                    // given
                    final long memberId = 1L;
                    final Member member =
                            Member.builder()
                                    .memberInfo(
                                            MemberInfo.builder().name("갑").password("1234").build())
                                    .authorities(
                                            List.of(
                                                    new Authority("STUDENT"),
                                                    new Authority("TEACHER")))
                                    .build();
                    member.withdrawal();

                    // when
                    when(memberRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(member));

                    // then
                    assertThatThrownBy(() -> withdrawlUsecase.withdrawl(memberId))
                            .isInstanceOf(AlreadyWithdrawlException.class);
                }
            }
        }

        @DisplayName("해당 회원이 존재하지 않는다면")
        @Nested
        class isNotExistMember {

            @DisplayName("NoMemberException 예외를 던진다.")
            @Test
            void throwNoMemberException() {
                // given
                final long memberId = 1L;
                final Member member =
                        Member.builder()
                                .memberInfo(MemberInfo.builder().name("갑").password("1234").build())
                                .authorities(
                                        List.of(new Authority("STUDENT"), new Authority("TEACHER")))
                                .build();

                // when
                when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

                // then
                assertThatThrownBy(() -> withdrawlUsecase.withdrawl(memberId))
                        .isInstanceOf(NoMemberException.class);
            }
        }
    }
}
