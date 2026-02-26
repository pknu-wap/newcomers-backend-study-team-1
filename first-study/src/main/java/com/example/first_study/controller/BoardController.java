package com.example.first_study.controller;

import com.example.first_study.Board;
import com.example.first_study.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; // @RequestMapping

import java.util.List; // board 여러개 담기 -> 리스트 필요

@Controller // 컨트롤러 명시
public class BoardController {
    @Autowired // 의존성 주입
    private BoardService boardService;

    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    // POST 요청이 오면 해당 메서드 실행
    @ResponseBody // view 대신 JSON으로 반환
    public Board create(@RequestBody Board board) { // JSON을 Board객체로 변환해서 받음
        return boardService.create(board); // service에 저장
    }

    @RequestMapping(value = "/boards", method = RequestMethod.GET)
    @ResponseBody
    public List<Board> findAll() {
        return boardService.findAll();
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Board findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @RequestMapping(value = "/boards/title/{title}", method = RequestMethod.GET)
    @ResponseBody
    public Board findByTitle(@PathVariable String title) {
        return boardService.findByTitle(title);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody Board board) {
        // @PathVariable Long id : 수정할 게시글의 id
        // @RequestBody Board board : 새로 넣을 Board(title, content)를 받음
        boardService.update(id, board.getTitle(), board.getContent());
        // 해당 id의 title, content 변경
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }



}
