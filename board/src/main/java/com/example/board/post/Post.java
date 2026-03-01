package com.example.board.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity//DB와 연결
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동생성
    @Id // 기본키(pk)
    private Long id;
    @Column(nullable = false)//null값이 오지 못하게 제약조건을 걺
    private String title;
    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}
