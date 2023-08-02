package com.ohdab.mistakenote.mapper;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MistakeRecordMapper {

    List<AllMistakeNoteInfoDto> findAllMistakeNoteInfo(List<Long> mistakeNoteIdList);
}
