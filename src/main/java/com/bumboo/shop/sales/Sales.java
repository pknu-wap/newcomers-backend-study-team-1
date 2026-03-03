package com.bumboo.shop.sales;

import com.bumboo.shop.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer price;
    private Integer count;

    /*
    @ManyToOne 테이블은 분리된 그대로, 객체가 포함 가능한 데이터의 종류(다른 DB의 객체)를 늘린다

    LAZY vs EAGER 작동 방식

    LAZY - 최초 해당하는 엔티티만 1회 조회 한 후 + Proxy생성( 다른 테이블의 ID만 들어있는 임시 객체)
    이후 종속된 다른 테이블의 엔티티가 필요하면, 다른 테이블 객체 수만큼(N번) 조회

    EAGER - 대상 엔티티 1회 조회, 그 후 연관된 객체 개별 쿼리 N번 조회

    기본적으로 LAZY 로딩 방식을 권장하는 이유 : 예측 불가한 상황을 방지 할 수 있다
    EAGER(한번에 전부 조회하라)는 데이터가 수 만건 이상 넘어갈 경우에 메모리가 터질 위험이 있다.

    N + 1 문제를 해결하는 JOIN쿼리문은 LAZY/EAGER 과 달리 1번의 쿼리문으로 관련 데이터를 모두 찾아오게 명령 가능하다.
    JOIN 문법도 불필요한 데이터들까지 전부 가져올 수 있으므로, 필수적인 연관 관계에서만 사용하는 것이 좋다.

    정리 ) 1.기본은 무조건 LAZY - 예측 불가한 상황 방지
    2. 성능상 N + 1 쿼리 문제가 심하면 ? - Fetch JOIN 검토
    3. JOIN을 걸기에는 복잡하게 얽힌 테이블이 많거나, 데이터의 수가 너무 많다면?
    - 그냥 DB들을 조회해서 필요한 데이터들 합체하기
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(
            name="member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    //foreign key 컬럼은 다른 테이블의 내용을 참조한다.
    )
    private Member member;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
