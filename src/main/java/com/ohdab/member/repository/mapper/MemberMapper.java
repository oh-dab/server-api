package com.ohdab.member.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;

@Mapper
public interface MemberMapper {

    List<StudentInfoDto> findAllStudent(List<Long> studentIdList);
}
