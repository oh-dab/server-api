package com.ohdab.member.repository.mapper;

import static com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    List<StudentInfoDto> findAllStudent(List<Long> studentIdList);
}
