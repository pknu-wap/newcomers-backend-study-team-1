package com.example.first_study.service;

import com.example.first_study.Board;
import com.example.first_study.repository.BoardRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 묶음으로 작업 처리(수정/삭제)

import java.util.List;
import java.util.Optional;

@Setter
@Getter

@Service // Bean 등록
public class BoardService {
    private final BoardRepository boardRepository; // 레파지토리 생성

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    } // 의존성 주입 << 더 공부!!!!

    public Board create(Board board) { // 글 생성(저장) - create
        board.onCreate(); // createdAt에 현재 시간 저장(수동으로 호출해야함)
        // @PrePersist 사용 가능!!!! 공부!!!!!!!!!!!!!!!!!!!!!!!!!
        return boardRepository.save(board); // save() : JPA 기본 제공
    }

    public List<Board> findAll() { // 전체 조회 - read
        return boardRepository.findAll(); // findAll() : JPA 기본 제공
    }

    public Board findById(Long id) { // id로 단일 조회 - read
        Optional<Board> result = boardRepository.findById(id); // findById() : JPA 기본 제공
        if (result.isEmpty()) {
            throw new InvalidBoardException("해당 id의 게시글이 없습니다.");
        }
        return result.get();
    }
    // jpa가 Board가 아닌 Optional<Board>로 값을 반환
    // 그 안에 값이 있을수도 있고 없을수도 있음
    // -> 없으면 예외처리, 있으면 Board로 반환


    public Board findByTitle(String title) { // title로 조회 - read
        Optional<Board> result = boardRepository.findByTitle(title); // findByTitle은 jpa 내장 x -> 선언 필요
        if (result.isEmpty()) {
            throw new InvalidBoardException("해당 제목의 게시글이 없습니다.");
        }
        return result.get();
    }
    // jpa가 Board가 아닌 Optional<Board>로 값을 반환
    // 그 안에 값이 있을수도 있고 없을수도 있음
    // -> 없으면 예외처리, 있으면 Board로 반환

    @Transactional // update에 자주 쓰이는 어노테이션
    // 자동 롤백 가능(예외 발생시), 알아서 변경 내용 db 전송(처음 조회와 마지막 조회의 변경 내용 감지)
    public void update(Long id, String title, String content) {
        Optional<Board> result = boardRepository.findById(id); // findById() : JPA 기본 제공
        if (result.isEmpty()) {
            throw new InvalidBoardException("해당 id의 게시글이 없습니다.");
        }
        Board board = result.get();
        board.setTitle(title);
        board.setContent(content); // 객체 값 변경 -> 트랜잭션 끝날 때 변경 내용 db 전송
    }

    @Transactional
    public void delete(Long id) {
        Optional<Board> result = boardRepository.findById(id); // findById() : JPA 기본 제공
        if (result.isEmpty()) {
            throw new InvalidBoardException("해당 id의 게시글이 없습니다.");
        }
        Board board = result.get(); // 해당 id의 글을 찾아서 board 변수에 넣음
        boardRepository.delete(board); // delete() : JPA 기본 제공
    }

}
