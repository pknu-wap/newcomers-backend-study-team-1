package com.bumboo.shop.Member;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

/*
 [Spring Security 핵심 서비스]
 UserDetailsService 인터페이스를 구현하여 스프링 시큐리티의 인증 프로세스와 DB를 연결함.
 */
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     회원가입 비즈니스 로직
     보안 핵심: 패스워드는 해시 함수(BCrypt)로 암호화하여 저장.
     (DB가 탈취되더라도 사용자 비밀번호를 알 수 없도록 설계)
     */
    public void RegisterMember(@ModelAttribute Member member){
        String hash = passwordEncoder.encode(member.getPassword());
        member.setPassword(hash);

        memberRepository.save(member);
    }

    /*
     Spring Security의 인증 단계에서 호출되는 메서드
     유저가 입력한 username으로 DB에서 정보를 찾아 UserDetails 객체로 변환함.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Member user = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 유저"));

        // 권한 부여
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        // 기본 User 객체에는 없는 displayName을 다루기 위해 CustomUser로 확장
        String displayName = user.getDisplayName();
        var a = new CustomUser(user.getUsername(), user.getPassword(), authorities, displayName);
        return a;
    }

    /*
     extends : 객체 확장, 시큐리티의 기본 User 클래스를 상속받아 커스텀 필드 추가
     principal.displayName으로 접근할 수 있게 됨.
     */
    class CustomUser extends User{
        private String displayName;

        public String getDisplayName() {
            return displayName;
        }

        public CustomUser(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities,
                String displayName
        ) {
            super(username, password, authorities);
            this.displayName = displayName;
        }
    }

     /* DTO
     엔티티를 서비스 외부로 전달할 때 사용하는 데이터 전송용 객체.
     @Getter 어노테이션을 통해 외부(Controller)에서 필드에 접근 가능하도록 함.
     현재 이론/사용 만 공부하였고 실제 적용은 추후 할 예정.
     */
    @Getter
    public static class MemberDTO{
        private String username;
        private String displayName;
        MemberDTO(String a, String b){
            this.username = a;
            this.displayName = b;
        }
    }
}
