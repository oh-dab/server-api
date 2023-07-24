package com.ohdab.entity.main.java.com.ohdab.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "MEMBER_AUTHORITY_LIST", joinColumns = @JoinColumn(name = "member_id"))
    private List<AuthorityJpa> authorities;

    @Builder
    public MemberJpa(String name, String password, List<AuthorityJpa> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }
}
