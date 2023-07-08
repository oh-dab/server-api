package com.ohdab;

import com.ohdab.domain.Member;
import com.ohdab.dto.LoginReqDto;
import com.ohdab.dto.LoginResDto;
import com.ohdab.helper.MemberHelperService;
import com.ohdab.port.in.LoginUsecase;
import com.ohdab.util.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService implements LoginUsecase {

    private final MemberHelperService memberHelperService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResDto login(LoginReqDto loginReqDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqDto.getName());
        Member member = memberHelperService.findExistingMember(loginReqDto.getName());
        if (!member.matchPassword(
                passwordEncoder, loginReqDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        Authentication authentication = createAuthentication(userDetails);
        return LoginResDto.builder()
                .memberId(member.getId())
                .jwtToken(jwtTokenProvider.createToken(authentication, member.getId()))
                .build();
    }

    private Authentication createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
