package com.ohdab.mistakenote.repository.mapper;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MistakeRecordMapper {

    List<AllMistakeNoteInfoDto> findAllMistakeNoteInfo(List<Long> mistakeNoteIdList);

    List<Integer> findNumbersWrongNTimes(GetNumberWrongNTimesDto.Request request);
}
