package com.bumboo.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {


    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/register")
    String register() {
        return "register.html";
    }

    @PostMapping("/member")
    String addMember(@ModelAttribute Member member){
        memberService.RegisterMember(member);
        return "redirect:/list";
    }

    @GetMapping("/login")
    String login(Authentication auth) {
        return "login.html";
    }

    @GetMapping("/my-page")
    String myPage(Authentication auth) {
        MemberService.CustomUser user = (MemberService.CustomUser) auth.getPrincipal();
        System.out.println(user.getDisplayName());
        return "myPage.html";
    }

    /*
     DTO(Data Transfer Object)
     엔티티를 직접 반환하지 않고 DTO를 거치는 이유:
     1. 보안: 비밀번호(password) 같은 민감한 정보를 API 응답에서 제외.
     2. 유연성: 화면에 필요한 데이터 구조가 엔티티와 다를 때 자유롭게 변경 가능.
     */
    @GetMapping("/user/1")
    @ResponseBody
    public MemberService.MemberDTO getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        // 엔티티에서 필요한 데이터만 뽑아 DTO 객체로 변환하여 전송
        var data = new MemberService.MemberDTO(result.getUsername(), result.getDisplayName());
        return data;
    }
    // 현재는 학습 단계라 Entity를 직접 받지만,
    // 차후 완성도 높이는 작업에서 입력 값 검증을 위해 MemberRequestDTO로 교체할 예정입니다.
}
