package com.ohdab.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class MemberEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded private ProfileVO profileVO;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "MEMBER_AUTHORITY_LIST", joinColumns = @JoinColumn(name = "member_id"))
    private List<AuthorityVO> authorities;

    @Builder
    public MemberEntity(ProfileVO profileVO, List<AuthorityVO> authorities) {
        this.profileVO = profileVO;
        this.authorities = authorities;
    }
}
