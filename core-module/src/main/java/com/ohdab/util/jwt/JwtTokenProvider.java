package com.ohdab.util.jwt;

import com.ohdab.util.jwt.exception.CustomWeakKeyException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@PropertySource("classpath:jwt.yml")
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private final UserDetailsService userDetailsService;

    @Value("${issuer}")
    private String issuer;

    @Value("${authorities-key}")
    private String authoritiesKey;

    @Value("${secret-key}")
    private String secretKey;

    @Value("${expiration-minutes}")
    private Long tokenValidityInMinutes;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (WeakKeyException e) {
            log.error("제공된 secret key가 256bit를 넘지 않습니다.", e);
            throw new CustomWeakKeyException(e);
        }
    }

    public String createToken(Authentication authentication, Long id) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidityInMinutes);
        return Jwts.builder()
                .setSubject(authentication.getName()) // JWT 토큰 제목
                .setIssuer(issuer) // JWT 토큰 발급자
                .setIssuedAt(now) // JWT 토큰 발급 시간
                .setExpiration(expiration)
                .claim("id", id)
                .claim(authoritiesKey, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // JWT 토큰 생성
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        // 클레임에서 권한 정보 가져오기
        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get(authoritiesKey).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 없거나 잘못되었습니다.", e);
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
