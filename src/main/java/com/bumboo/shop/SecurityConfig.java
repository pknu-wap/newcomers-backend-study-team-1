package com.bumboo.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 Spring Security 설정 클래스
 애플리케이션의 전반적인 보안 정책(인증, 인가, 암호화)을 구성함.

 CSRF 방어 일시 비활성화 상태(초기 개발 단계에서 POST/PUT 요청의 편의성을 위해 해제함)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/login","/register").permitAll()
                // 로그인과 회원가입 페이지는 누구나 접근 가능
                .anyRequest().authenticated()
                // 그 외의 모든 요청은 로그인한 사용자만 접근 가능
        );
        http.formLogin((form)
                -> form.loginPage("/login")
                // 사용자가 직접 만든 로그인 페이지 연결
                .defaultSuccessUrl("/list", true)
                // 로그인 성공 시 이동할 기본 경로
                .failureUrl("/login?error")
                // 로그인 실패 시 에러 파라미터와 함께 이동
        );
        http.logout(logout -> logout.logoutUrl("/logout"));
        // 기본 로그아웃 경로를 지정하고 세션 및 쿠키 무효화 자동 처리
        return http.build();
    }

    /*
     비밀번호 암호화 방식 정의 (Hashing)
     BCryptPasswordEncoder : 해시 함수를 사용하여 비밀번호를 암호화함.
     (단순 암호화하는 것을 넘어, 솔팅(Salting) 기법을 통해 레인보우 테이블 공격을 방어함)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
