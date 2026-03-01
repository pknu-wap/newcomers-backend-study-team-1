package com.example.board.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private String title;
    private String content;
}
//게시글 생성 요청 DTO
//
