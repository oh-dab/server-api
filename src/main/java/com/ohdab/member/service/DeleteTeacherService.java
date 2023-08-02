package com.ohdab.member.service;

import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeleteTeacherService implements DeleteTeacherUsecase {

    private final MemberRepository memberRepository;
    private final MemberHelperService memberHelperService;

    @Override
    public void deleteTeacherById(long id) {
        throwIfUnknownId(id);
        memberRepository.deleteById(id);
    }

    private void throwIfUnknownId(long id) {
        if (!memberHelperService.checkIfMemberExistById(memberRepository, id)) {
            throw new NoMemberException("Unknown member with id \"" + id + "\"");
        }
    }
}
