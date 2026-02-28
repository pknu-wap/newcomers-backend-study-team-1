package com.example.first_study.service;

public class InvalidBoardException extends RuntimeException{ // 커스텀 예외
    public InvalidBoardException(String message) { // 생성자, 에러 메시지를 문자열로 받음
        super(message); // RuntimeException(부모클래스)에 메시지 전달
    }
}

// 메시지 대신 HttpStatusCode 넣어서 응답상태 컨트롤 하기 공부!!!!!!!!
// -> https://github.com/Moadong/moadong/tree/main/backend/src/main/java/moadong/global/exception