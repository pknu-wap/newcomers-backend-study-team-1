package com.example.first_study.controller;

import com.example.first_study.Board;
import com.example.first_study.dto.BoardCreateRequestDTO;
import com.example.first_study.dto.BoardUpdateRequestDTO;
import com.example.first_study.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; // @RequestMapping

import java.util.List; // board 여러개 담기 -> 리스트 필요

// @Controller는 html 랜더링 시에 주로 사용
@RestController // 컨트롤러 명시

public class BoardController {
    @Autowired // 의존성 주입
    private BoardService boardService;

    // @RequestMapping(value = "/boards", method = RequestMethod.POST)
    @PostMapping("/boards")
    // POST 요청이 오면 해당 메서드 실행
    //@ResponseBody // view 대신 JSON으로 반환
    public Board create(@RequestBody BoardCreateRequestDTO board) { // JSON을 Board객체로 변환해서 받음
        return boardService.create(board); // service에 저장
    }

    //@RequestMapping(value = "/boards", method = RequestMethod.GET)
    @GetMapping("/boards")
    //@ResponseBody
    public List<Board> findAll() {
        return boardService.findAll();
    } // DTO로 한 번 감싸기!!!!!!! 내일 다시 시도...

    //@RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    @GetMapping("/boards/{id}")
    //@ResponseBody
    public Board findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

    //@RequestMapping(value = "/boards/title/{title}", method = RequestMethod.GET)
    @GetMapping("/boards/title/{title}")
    //@ResponseBody
    public Board findByTitle(@PathVariable String title) {
        return boardService.findByTitle(title);
    }

    //@RequestMapping(value = "/boards/{id}", method = RequestMethod.PUT)
    @PatchMapping("/boards/{id}")
    //@ResponseBody
    public void update(@PathVariable Long id, @RequestBody BoardUpdateRequestDTO board) {
        // @PathVariable Long id : 수정할 게시글의 id
        // @RequestBody Board board : 새로 넣을 Board(title, content)를 받음
        boardService.update(id, board);
        // 해당 id의 title, content 변경
    }

    //@RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/boards/{id}")
    //@ResponseBody
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }



}



// Entity를 데이터 전달 목적으로 받으면 안 됨
// DTO에 대해 더 공부!!!!!!!!! (왜 사용해야할까?)

// PUT 메소드 대신 PATCH 메소드 구현 공부!!!!! (+ http method 방식의 차이

/*
PUT: 자원의 전체를 업데이트(기존 값을 모두 알고 있어야함)
PATCH: 자원의 일부를 업데이트(변경할 값만 알아도 됨)
*/