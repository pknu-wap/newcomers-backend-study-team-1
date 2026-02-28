package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public List<Board> get() {
        return boardService.getAll();
    }

    @GetMapping("/boards/{id}")
    public Board getById(@PathVariable Long id) {
        return boardService.getById(id);
    }

    @PostMapping("/boards")
    public Board post(@RequestBody Board board) {
        return boardService.create(board);
    }
}