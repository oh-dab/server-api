package com.ohdab.member.service.usecase;

import com.ohdab.member.service.dto.MemberDtoForAddTeacher;

public interface AddTeacherUsecase {

    void addTeacher(MemberDtoForAddTeacher.Request memberDtoForAddTeacherDto);
}
