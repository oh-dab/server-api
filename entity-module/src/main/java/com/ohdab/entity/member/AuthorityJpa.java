package com.ohdab.entity.member;

import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityJpa {

    private String role;
}
