package com.ohdab.member.repository.mapper;

import static com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;

import com.ohdab.classroom.service.dto.ClassroomDetailDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    List<StudentInfoDto> findAllStudentForMistakeNoteInfo(List<Long> studentIdList);

    List<ClassroomDetailDto.StudentInfo> findAllStudentForClassroomInfo(List<Long> studentIdList);
}
