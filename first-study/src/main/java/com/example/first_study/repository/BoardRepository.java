package com.example.first_study.repository;

import com.example.first_study.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // 값이 있을수도 있고 없을 수도 있을 때 사용


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // <레포지토리가 관리하는 entity 타입, 해당 entity의 pk 타입>
    // save, findAll, findById 등등 사용 가능
        Optional<Board> findByTitle(String title);
        // 쿼리 메서드, Spring이 알아서 SQL 생성(JPA가 이름 패턴 인식)
    }