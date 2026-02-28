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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Board {

    // strategy 명시!!!!!!!!!!!
    @Id // pk
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // pk값 db에서 자동 생성
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