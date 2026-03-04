package com.example.board.controller;

public class BoardRequest {

    private String title;
    private String content;

    // 기본 생성자 (Spring에서 필요)
    public BoardRequest() {
    }

    // Getter 직접 작성
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}