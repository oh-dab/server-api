package com.ohdab.mistakenote.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.when;

import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetNumbersWrongNTimesService.class})
class GetNumbersWrongNTimesServiceTest {

    @Autowired private GetNumbersWrongNTimesUsecase getNumbersWrongNTimesUsecase;

    @MockBean private MistakeRecordMapper mistakeRecordMapper;

    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @Nested
    class getNumbersWrongNTimes_메서드는 {

        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @Nested
        class 요청받은_범위_내에서_N번_이상_틀린_문제가_존재한다면 {

            @DisplayName("틀린 문제번호들을 쉼표로 구분하여 공백없는 문자열로 반환한다")
            @Test
            void 틀린_문제번호들을_쉼표로_구분하여_공백없는_문자열로_반환한다() {
                // given
                final GetNumbersWrongNTimesDto.Request requestDto =
                        GetNumbersWrongNTimesDto.Request.builder()
                                .mistakeNoteId(1L)
                                .count(2)
                                .from(10)
                                .to(20)
                                .build();
                final List<Integer> numbersWrongNTimes = List.of(10, 11, 12, 19, 20);

                // when
                when(mistakeRecordMapper.findNumbersWrongNTimes(requestDto))
                        .thenReturn(numbersWrongNTimes);
                GetNumbersWrongNTimesDto.Response result =
                        getNumbersWrongNTimesUsecase.getNumbersWrongNTimes(requestDto);

                // then
                assertThat(result).extracting("wrongNumbers").isEqualTo("10,11,12,19,20");
            }
        }

        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @Nested
        class 요청받은_범위_내에서_N번_이상_틀린_문제가_없다면 {

            @DisplayName("NoNumbersWrongNTimesException 예외를 발생시킨다.")
            @Test
            void NoNumbersWrongNTimesException_예외를_발생시킨다() {
                // given
                final GetNumbersWrongNTimesDto.Request requestDto =
                        GetNumbersWrongNTimesDto.Request.builder()
                                .mistakeNoteId(1L)
                                .count(2)
                                .from(10)
                                .to(20)
                                .build();
                final List<Integer> numbersWrongNTimes = new ArrayList<>();

                // when
                when(mistakeRecordMapper.findNumbersWrongNTimes(requestDto))
                        .thenThrow(NoNumbersWrongNTimesException.class);
                Throwable thrown =
                        catchException(
                                () ->
                                        getNumbersWrongNTimesUsecase.getNumbersWrongNTimes(
                                                requestDto));

                // then
                assertThat(thrown).isInstanceOf(NoNumbersWrongNTimesException.class);
            }
        }
    }
}
