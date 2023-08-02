package com.ohdab.member.service.usecase;

import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import java.util.List;

public interface GetTeacherListUsecase {

    List<MemberDtoForGetTeacherList.Response> getTeacherList();
}
