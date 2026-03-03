package com.bumboo.shop.Member;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
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


    public void RegisterMember(@ModelAttribute Member member){
        String hash = passwordEncoder.encode(member.getPassword());
        member.setPassword(hash);

        memberRepository.save(member);
    }

    /*
     Spring Security의 인증 단계에서 호출되는 메서드
     loadUserByUsername = DB에서 데이터를 조회해서, spring security가 이해 가능한 객체로 만드는 과정.

     User/CustomUser 객체 = 인증을 위한 최종 규젹서,
     spring security는 데이만 던져주면 이해하지 못함.

     유저가 입력한 username으로 DB에서 정보를 찾아 UserDetails 객체로 변환함.

     spring security 내부의 핵심 엔진 AuthenticationManager
     에서 사용자가 입력한 정보와 UserDetailsService가 반환한 정보를 자동으로 대조하여
     인증을 처리하고 쿠키를 발급 해 준다.

     개발자는 클래스 내부에 (loadUserByUsername + User or CustomUser) 객체 생성 만
      구현해놓으면, 나머지는 알아서 내부적 처리를 해준다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Member user = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 유저"));

        // 권한 부여
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        // 기본 User 객체에는 없는 displayName을 다루기 위해 CustomUser로 확장
        // id변수도 추가
        String displayName = user.getDisplayName();
        Long id = user.getId();
        var a = new CustomUser(user.getUsername(), user.getPassword(), authorities, displayName, id);
        return a;
    }

    public class CustomUser extends User{
        private String displayName; //User 객체에 없는 커스텀 변수들
        private Long id;

        public String getDisplayName() {
            return displayName;
        }
        public Long getId(){
            return id;
        }

        //생성자
        public CustomUser(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities,
                String displayName,
                Long id
        ) {
            //super( 부모 파라미터), 상속받은 부모 요소의 생성자 역할을 함
            super(username, password, authorities);
            //자식요소의 멤버 변수 초기화도 따로
            this.displayName = displayName;
            this.id = id;
        }
    }

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
