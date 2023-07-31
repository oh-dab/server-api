package com.ohdab.member.service;

import com.ohdab.member.service.dto.MemberDto;
import com.ohdab.member.service.helper.MemberHelperService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberHelperService memberHelperService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        MemberDto memberDto = memberHelperService.findExistingMember(name);
        return createUserDetails(memberDto);
    }

    private UserDetails createUserDetails(MemberDto memberDto) {
        return new User(
                memberDto.getName(),
                memberDto.getPassword(),
                mapToSimpleGrandAuthority(memberDto.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> mapToSimpleGrandAuthority(List<String> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}
