package com.mysite.sbb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(
                        (httpBasic) -> httpBasic.disable())
                // ).disable() // rest api -> 기본 설정 disable
                .csrf((csrf) -> csrf.disable()) // csrf disable 처리
                .cors((cors) -> cors.disable()) // cors disable 처리
                .sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionFixation().changeSessionId() // 세션 고정 공격 방지
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 활용 안함
                )
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers("/h2-console/**").permitAll() // h2-console 에 대한 권한 해제
                                .requestMatchers("/user/signup").permitAll() // 가입, 로그인에 대한 권한 해제
                                .anyRequest().hasRole("USER") // 나머지 요청에 대해 USER ROLE 을 가져야만 접근 가능
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}