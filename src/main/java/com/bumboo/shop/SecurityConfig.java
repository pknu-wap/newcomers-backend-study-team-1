package com.bumboo.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((authorize) -> authorize
                // 1. 정적 리소스 (CSS, JS 등) 허용
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // 2. 로그인/회원가입 페이지 + 회원가입 처리 API(/member) 허용
                .requestMatchers("/login", "/register", "/member").permitAll()

                .anyRequest().authenticated()
        );
        http.formLogin((form)
                -> form.loginPage("/login")
                .defaultSuccessUrl("/list", true)
                .failureUrl("/login?error")

        );
        http.logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
