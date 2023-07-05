package com.ohdab.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile {

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_password")
    private String password;
}
