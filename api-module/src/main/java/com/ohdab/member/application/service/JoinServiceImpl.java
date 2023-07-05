package com.ohdab.member.application.service;

import com.ohdab.member.application.exception.DuplicatedMemberException;
import com.ohdab.member.application.service.dto.JoinReqDto;
import com.ohdab.member.application.service.spi.JoinService;
import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.MemberProfile;
import com.ohdab.member.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void join(JoinReqDto joinReqDto) {
        checkDuplicatedMember(joinReqDto.getName());
        // TODO : 권한 부여 로직을 작성해야 함
        List<Authority> authorities = new ArrayList<>();
        authorities.add(Authority.builder().role("ADMIN").build());
        Member member =
                Member.builder()
                        .memberProfile(
                                MemberProfile.builder()
                                        .name(joinReqDto.getName())
                                        .password(passwordEncoder.encode(joinReqDto.getPassword()))
                                        .build())
                        .authorityList(authorities)
                        .build();
        userRepository.save(member);
    }

    private void checkDuplicatedMember(String name) {
        Optional<Member> member = userRepository.findByMemberProfile_Name(name);
        if (member.isPresent()) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }
    }
}
