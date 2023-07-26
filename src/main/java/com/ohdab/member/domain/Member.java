package com.ohdab.member.domain;

import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "member_name", nullable = false))
    @AttributeOverride(
            name = "password",
            column = @Column(name = "member_password", nullable = false))
    private MemberInfo memberInfo;

    @ElementCollection
    @CollectionTable(name = "MEMBER_AUTHORITY", joinColumns = @JoinColumn(name = "member_id"))
    private List<Authority> authorities;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(MemberInfo memberInfo, List<Authority> authorities) {
        setMemberInfo(memberInfo);
        setAuthorities(authorities);
        this.status = MemberStatus.ACTIVE;
    }

    private void setAuthorities(List<Authority> authorities) {
        if (authorities.isEmpty()) {
            throw new IllegalArgumentException("권한은 반드시 추가해야함");
        }
        this.authorities = authorities;
    }

    private void setMemberInfo(MemberInfo memberInfo) {
        if (memberInfo.getName().length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \""
                            + memberInfo.getName().length()
                            + "\"");
        }
    }

    public boolean matchPassword(
            PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void withdrawal() {
        if (this.status == MemberStatus.INACTIVE) {
            throw new IllegalStateException("예외");
        }
        this.status = MemberStatus.INACTIVE;
    }
}
