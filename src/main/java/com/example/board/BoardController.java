package com.example.board;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class BoardController {
    BoardRepository repo;

    public BoardController(BoardRepository boardRepository) {
        this.repo = boardRepository;
    }

    public void CreateArticle() {
        Article article = new Article();
        article.id = 1L;
        article.title = "글 제목";
        article.content = "내요123ㅇ";
        article.createdAt = LocalDateTime.now();

        repo.save(article);
    }
}
