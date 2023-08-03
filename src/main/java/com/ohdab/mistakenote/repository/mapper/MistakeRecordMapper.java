package com.ohdab.mistakenote.repository.mapper;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MistakeRecordMapper {

    List<AllMistakeNoteInfoDto> findAllMistakeNoteInfo(List<Long> mistakeNoteIdList);
}
