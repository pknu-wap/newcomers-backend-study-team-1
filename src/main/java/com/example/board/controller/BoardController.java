package com.example.board.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 🔥 생성자 직접 작성 (중요)
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.findAll();
    }

    @PostMapping
    public Board createBoard(@RequestBody com.example.board.controller.BoardRequest request) {
        return boardService.save(request.getTitle(), request.getContent());
    }
}