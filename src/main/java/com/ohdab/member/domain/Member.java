package com.ohdab.member.domain;

import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.exception.AlreadyWithdrawlException;
import com.ohdab.member.exception.MemberContentOverflowException;
import com.ohdab.member.exception.NoAuthorityException;
import io.jsonwebtoken.lang.Assert;
import java.util.ArrayList;
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
    private List<Authority> authorities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(MemberInfo memberInfo, List<Authority> authorities) {
        Assert.notNull(memberInfo, ExceptionEnum.IS_NULL.getMessage());
        Assert.notNull(authorities, ExceptionEnum.IS_NULL.getMessage());
        setMemberInfo(memberInfo);
        setAuthorities(authorities);
        this.status = MemberStatus.ACTIVE;
    }

    private void setAuthorities(List<Authority> authorities) {
        if (authorities.isEmpty()) {
            throw new NoAuthorityException(ExceptionEnum.NO_AUTHORITY.getMessage());
        }
        this.authorities = authorities;
    }

    private void setMemberInfo(MemberInfo memberInfo) {
        if (memberInfo.getName().length() > 20) {
            throw new MemberContentOverflowException(
                    ExceptionEnum.MEMBER_CONTENT_OVERFLOW.getMessage());
        }
        this.memberInfo = memberInfo;
    }

    public boolean matchPassword(
            PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void withdrawal() {
        if (this.status == MemberStatus.INACTIVE) {
            throw new AlreadyWithdrawlException(ExceptionEnum.ALREADY_WITHDRAWL.getMessage());
        }
        this.status = MemberStatus.INACTIVE;
    }
}
