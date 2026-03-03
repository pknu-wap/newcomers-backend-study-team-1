package com.bumboo.shop.Item;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String keyword);

    /*@Query() 내부에 직접 Sql 문법을 써서, fullTextSearch()의 함수 부분을 표현함.
    여기서는 JPA 못쓰는 이유? - DB마다 문법이 다 달라서 일관성 있는 환경에서만 가능한 JPA는 사용 불가

        아직 Sql문법은 배우지 않아, 외부 자료 참조했습니다.
    */
    @Query(value = "SELECT * FROM item WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            countQuery = "SELECT count(*) FROM item WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    Page<Item> fullTextSearch(@Param("keyword") String keyword, Pageable pageable);
    //결과 값을 Page<> 자료형으로 담아서, 검색 결과 화면도 동일하게 페이지네이션 적용을 함.
    //@Param()은 DB로 데이터를 보낼 때 사용,
    //사용 위치 : @Query()내부의 파라미터로 전달

    //@RequestParam : 브라우저 -> 서버
    //@Param  : 서버 -> DB
}
