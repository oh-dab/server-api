package com.ohdab.mistakenote.repository.mapper;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class MistakeRecordMapperTest {

    @Autowired private MistakeRecordMapper mistakeRecordMapper;

    @DisplayName("전체 학생에 대한 오답노트를 조회한다.")
    @Test
    void 교재_상세조회() {
        // given
        List<Long> mistakeNoteIdList = new ArrayList<>();
        mistakeNoteIdList.add(1L);
        mistakeNoteIdList.add(2L);
        mistakeNoteIdList.add(3L);
        // when
        List<AllMistakeNoteInfoDto> result =
                mistakeRecordMapper.findAllMistakeNoteInfo(mistakeNoteIdList);

        // then
    }
}
