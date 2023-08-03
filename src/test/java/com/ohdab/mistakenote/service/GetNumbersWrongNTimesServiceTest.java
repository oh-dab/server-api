package com.ohdab.mistakenote.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.exception.NumberIsOutOfRangeException;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumberWrongNTimesUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetNumberWrongNTimesService.class})
class GetNumbersWrongNTimesServiceTest {

    @Autowired private GetNumberWrongNTimesUsecase getNumberWrongNTimesUsecase;

    @MockBean private MistakeRecordMapper mistakeRecordMapper;
    @MockBean private WorkbookRepository workbookRepository;

    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @Nested
    class getNumbersWrongNTimes_메서드는 {

        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @Nested
        class 요청받은_범위가_해당_교재의_문제범위에_속하고 {

            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            @Nested
            class N번_이상_틀린_문제가_존재한다면 {
                @DisplayName("틀린 문제번호들을 쉼표로 구분하여 공백없는 문자열로 반환한다")
                @Test
                void 틀린_문제번호들을_쉼표로_구분하여_공백없는_문자열로_반환한다() {
                    // given
                    final GetNumberWrongNTimesDto.Request requestDto =
                            GetNumberWrongNTimesDto.Request.builder()
                                    .workbookId(1L)
                                    .mistakeNoteId(2L)
                                    .count(2)
                                    .from(10)
                                    .to(20)
                                    .build();

                    final Workbook workbook =
                            Workbook.builder()
                                    .classroomId(new ClassroomId(10L))
                                    .workbookInfo(
                                            WorkbookInfo.builder()
                                                    .name("책이름")
                                                    .description("설명")
                                                    .startingNumber(1)
                                                    .endingNumber(1000)
                                                    .build())
                                    .build();

                    final List<Integer> numberWrongNTimes = List.of(10, 11, 12, 19, 20);

                    // when
                    when(workbookRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(workbook));
                    when(mistakeRecordMapper.findNumbersWrongNTimes(requestDto))
                            .thenReturn(numberWrongNTimes);
                    GetNumberWrongNTimesDto.Response result =
                            getNumberWrongNTimesUsecase.getNumberWrongNTimes(requestDto);

                    // then
                    assertThat(result).extracting("wrongNumber").isEqualTo("10,11,12,19,20");
                }
            }

            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            @Nested
            class N번_이상_틀린_문제가_없다면 {

                @DisplayName("NoNumbersWrongNTimesException 예외를 던진다.")
                @Test
                void NoNumbersWrongNTimesException_예외를_발생시킨다() {
                    // given
                    final GetNumberWrongNTimesDto.Request requestDto =
                            GetNumberWrongNTimesDto.Request.builder()
                                    .workbookId(1L)
                                    .mistakeNoteId(2L)
                                    .count(2)
                                    .from(10)
                                    .to(20)
                                    .build();

                    final Workbook workbook =
                            Workbook.builder()
                                    .classroomId(new ClassroomId(10L))
                                    .workbookInfo(
                                            WorkbookInfo.builder()
                                                    .name("책이름")
                                                    .description("설명")
                                                    .startingNumber(1)
                                                    .endingNumber(1000)
                                                    .build())
                                    .build();

                    final List<Integer> numberWrongNTimes = new ArrayList<>();

                    // when
                    when(workbookRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(workbook));
                    when(mistakeRecordMapper.findNumbersWrongNTimes(requestDto))
                            .thenReturn(numberWrongNTimes);
                    Throwable thrown =
                            catchException(
                                    () ->
                                            getNumberWrongNTimesUsecase.getNumberWrongNTimes(
                                                    requestDto));

                    // then
                    assertThat(thrown).isInstanceOf(NoNumbersWrongNTimesException.class);
                }
            }
        }

        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        @Nested
        class 요청받은_범위가_해당_교재의_문제범위에_속하지_않는다면 {

            @DisplayName("NumberIsOutOfRangeException 예외를 던진다.")
            @Test
            void NumberIsOutOfRangeException_예외를_던진다() {
                // given
                final GetNumberWrongNTimesDto.Request requestDto =
                        GetNumberWrongNTimesDto.Request.builder()
                                .workbookId(1L)
                                .mistakeNoteId(2L)
                                .count(2)
                                .from(20)
                                .to(2000)
                                .build();

                final Workbook workbook =
                        Workbook.builder()
                                .classroomId(new ClassroomId(10L))
                                .workbookInfo(
                                        WorkbookInfo.builder()
                                                .name("책이름")
                                                .description("설명")
                                                .startingNumber(1)
                                                .endingNumber(1000)
                                                .build())
                                .build();

                // when
                when(workbookRepository.findById(anyLong()))
                        .thenReturn(Optional.ofNullable(workbook));
                Throwable thrown =
                        catchException(
                                () -> getNumberWrongNTimesUsecase.getNumberWrongNTimes(requestDto));

                // then
                assertThat(thrown).isInstanceOf(NumberIsOutOfRangeException.class);
            }
        }
    }
}
