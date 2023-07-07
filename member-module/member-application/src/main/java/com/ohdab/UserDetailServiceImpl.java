package com.ohdab;

import com.ohdab.domain.Member;
import com.ohdab.helper.MemberHelperService;
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
        Member member = memberHelperService.findExistingMember(name);
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member) {
        return new User(
                member.getProfile().getName(),
                member.getProfile().getPassword(),
                mapToSimpleGrandAuthority(member));
    }

    private List<SimpleGrantedAuthority> mapToSimpleGrandAuthority(Member member) {
        return member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
    }
}
