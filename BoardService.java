package com.example.board.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 🔥 생성자 직접 작성
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 작성
    public Board save(String title, String content) {
        Board board = new Board(title, content);
        return boardRepository.save(board);
    }

    // 게시글 전체 조회
    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}