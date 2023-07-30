package com.ohdab.member.service.usecase;

import com.ohdab.member.domain.Member;
import com.ohdab.member.service.dto.TeacherReqDto;
import java.util.List;

public interface GetTeacherListUsecase {

    List<Member> getTeacherList();

    void addTeacher(TeacherReqDto teacherReqDto);
}
