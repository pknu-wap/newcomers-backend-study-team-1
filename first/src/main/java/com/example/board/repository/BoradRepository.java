package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoradRepository extends JpaRepository<Board, Long> {
}
