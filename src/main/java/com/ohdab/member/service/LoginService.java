package com.ohdab.member.service;

import com.ohdab.core.util.jwt.JwtTokenProvider;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForLogin;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.LoginUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements LoginUsecase {

    private final MemberRepository memberRepository;
    private final MemberHelperService memberHelperService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDtoForLogin.Response login(MemberDtoForLogin.Request loginReqDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqDto.getName());
        Member member =
                memberHelperService.findExistingMemberByName(
                        memberRepository, loginReqDto.getName());
        if (!member.matchPassword(
                passwordEncoder, loginReqDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        Authentication authentication = createAuthentication(userDetails);
        return MemberDtoForLogin.Response.builder()
                .memberId(member.getId())
                .jwtToken(jwtTokenProvider.createToken(authentication, member.getId()))
                .build();
    }

    private Authentication createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
