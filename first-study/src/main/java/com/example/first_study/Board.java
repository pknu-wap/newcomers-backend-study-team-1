package com.example.first_study;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Board {

    @Id // pk
    @GeneratedValue // pk값 db에서 자동 생성
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    public void onCreate() { // 자동 호출 안됨, 수동 호출 필수
        this.createdAt = LocalDateTime.now(); // createdAt에 현재 시간 넣기
    }
}