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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetNumberWrongNTimesService.class})
class GetNumberWrongNTimesServiceTest {

    @Autowired private GetNumberWrongNTimesUsecase getNumberWrongNTimesUsecase;

    @MockBean private MistakeRecordMapper mistakeRecordMapper;
    @MockBean private WorkbookRepository workbookRepository;

    @DisplayName("getNumbersWrongNTimes 메서드는")
    @Nested
    class getNumbersWrongNTimes {

        @DisplayName("요청받은 범위가 해당 교재의 문제범위에 속하고")
        @Nested
        class ifInRange {

            @DisplayName("N번 이상 틀린 문제가 존재한다면")
            @Nested
            class ifResultIsExist {
                @DisplayName("틀린 문제번호들을 쉼표로 구분하여 공백없는 문자열로 반환한다")
                @Test
                void success() {
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
                    assertThat(result)
                            .extracting(GetNumberWrongNTimesDto.Response::getWrongNumber)
                            .isEqualTo("10,11,12,19,20");
                }
            }

            @DisplayName("N번 이상 틀린 문제가 없다면")
            @Nested
            class ifNoResult {

                @DisplayName("NoNumbersWrongNTimesException 예외를 던진다.")
                @Test
                void throwNoNumbersWrongNTimesException() {
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

        @DisplayName("요청받은 범위가 해당 교재의 문제범위에 속하지 않는다면")
        @Nested
        class ifIsNotInRange {

            @DisplayName("NumberIsOutOfRangeException 예외를 던진다.")
            @Test
            void trowNumberIsOutOfRangeException() {
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
