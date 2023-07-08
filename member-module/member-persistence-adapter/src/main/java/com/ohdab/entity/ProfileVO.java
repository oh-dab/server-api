package com.ohdab.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileVO {

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_password")
    private String password;
}
