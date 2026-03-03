package com.bumboo.shop.sales;

import com.bumboo.shop.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findAllBymemberIdOrderByCreatedAtAsc(Long memberId);

    //JOIN FETCH , N+1번 쿼리 조회 문제를 해결 할 수 있다.
    //@Query()내부에 JPQL 문법으로, Sales 조회 시 member도 한꺼번에 조회하라는 쿼리문을 직접 작성하였다.
    @Query(value="SELECT s FROM Sales s JOIN FETCH s.member")
    List<Sales> customFindAll();
}
