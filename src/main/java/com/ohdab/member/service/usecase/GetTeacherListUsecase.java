package com.ohdab.member.service.usecase;

import com.ohdab.member.service.dto.MemberDto;
import com.ohdab.member.service.dto.TeacherReqDto;
import java.util.List;

public interface GetTeacherListUsecase {

    List<MemberDto> getTeacherList();

    void addTeacher(TeacherReqDto teacherReqDto);
}
