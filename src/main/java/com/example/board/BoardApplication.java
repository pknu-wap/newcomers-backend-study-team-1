package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {
    static BoardController controller;

    public BoardApplication(BoardController controller) {
        BoardApplication.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}
