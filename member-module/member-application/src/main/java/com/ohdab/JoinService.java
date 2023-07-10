package com.ohdab;

import com.ohdab.domain.Member;
import com.ohdab.domain.authority.Authority;
import com.ohdab.domain.profile.Profile;
import com.ohdab.dto.JoinReqDto;
import com.ohdab.exception.DuplicatedMemberException;
import com.ohdab.port.in.JoinUsecase;
import com.ohdab.port.out.FindMemberPort;
import com.ohdab.port.out.SaveMemberPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService implements JoinUsecase {

    private final PasswordEncoder passwordEncoder;
    private final FindMemberPort findMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public void join(JoinReqDto joinReqDto) {
        checkDuplicatedMember(joinReqDto.getName());
        Member member = createMember(joinReqDto, createAuthorities(joinReqDto));
        saveMemberPort.save(member);
    }

    private List<Authority> createAuthorities(JoinReqDto joinReqDto) {
        List<String> roleList = joinReqDto.getRole();
        return roleList.stream()
                .map(role -> Authority.builder().role(role).build())
                .collect(Collectors.toList());
    }

    private Member createMember(JoinReqDto joinReqDto, List<Authority> authorities) {
        return Member.builder()
                .profile(createMemberProfile(joinReqDto))
                .authorities(authorities)
                .build();
    }

    private Profile createMemberProfile(JoinReqDto joinReqDto) {
        return Profile.builder()
                .name(joinReqDto.getName())
                .password(passwordEncoder.encode(joinReqDto.getPassword()))
                .build();
    }

    private void checkDuplicatedMember(String name) {
        Optional<Member> member = findMemberPort.findByName(name);
        if (member.isPresent()) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }
    }
}
