package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoradRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoradRepository boardRepository;

    public BoardService(BoradRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    public Board getById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board create(Board board) {
        return boardRepository.save(board);
    }
}