package com.ohdab.core.util.security.config;

import com.ohdab.core.util.jwt.JwtTokenProvider;
import com.ohdab.core.util.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final String TEACHER = "TEACHER";
    private final String STUDENT = "STUDENT";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http.cors()
                    .configurationSource(corsConfigurationSource())
                    .and()
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    // member
                    .antMatchers("/members/join")
                    .permitAll()
                    .antMatchers("/members/login")
                    .permitAll()
                    .antMatchers("/members/**")
                    .hasAuthority(TEACHER)
                    // classroom
                    .antMatchers(HttpMethod.GET, "/classrooms/{classroom-id}/workbooks")
                    .hasAnyAuthority(STUDENT, TEACHER)
                    .antMatchers("/classrooms/**")
                    .hasAuthority(TEACHER)
                    // mistakeNote
                    .antMatchers(
                            HttpMethod.GET,
                            "/mistake-notes/workbooks/{workbook-id}/students/{student-id}")
                    .hasAnyAuthority(STUDENT, TEACHER)
                    .antMatchers(HttpMethod.GET, "/mistake-notes/**")
                    .hasAuthority(TEACHER)
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilterBefore(
                            new JwtAuthFilter(jwtTokenProvider),
                            UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("security error", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 허용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
