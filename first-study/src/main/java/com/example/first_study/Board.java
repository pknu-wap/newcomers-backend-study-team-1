package com.example.first_study;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
// @Setter // entity에 setter 넣으면 안 됨 -> update 메소드 추가해서 사용
// https://parkseryu.tistory.com/167
/*
캡슐화 무시
유지보수 어려움
-> 빌더패턴 사용!
*/
@AllArgsConstructor // 내 모든 필드를 파라미터로 받는 생성자 생성

@Builder

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity // entity가 붙은 클래스는 반드시 기본 생성자가 필요 -> @NoArgsConstructor 사용
/*
@NoArgsConstructor(access = AccessLevel.PROTECTED)
- jpa entity에서 주로 사용
- 외부 new Board() 막기
- jpa만 사용하게 함
*/

public class Board {

    // strategy 명시!!!!!!!!!!!
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk값 db에서 자동 생성
    // IDENTITY(AUTO_INCREMENT, Mysql에서 주로 사용), AUTO, SEQUENCE(H2 주로 사용), TABLE
    private Long id;
    private String title;
    private String content;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 이걸로 대체 가능
//    public void onCreate() { // 자동 호출 안됨, 수동 호출 필수
//        this.createdAt = LocalDateTime.now(); // createdAt에 현재 시간 넣기
//    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;

    }


//    private Long id;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String content;
//
//    private LocalDateTime createdAt;
//
}
/*
DTO
- 엔티티의 내부 구조를 숨길 수 있다.
- 전송할 데이터 양을 최적화 할 수 있다.
- 유연한 변경 가능
컬럼에 있는 값은 dto 뿐만 아니라 entity에도 있어야함
dto는 요청, 응답에 사용되는 값만 담음
*/