package com.bumboo.shop.Member;

import com.bumboo.shop.sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SoftDelete;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@SoftDelete(columnName = "deleted")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String displayName;

    //@ManyToOne의 반대 테이블에 작성하는 @OneToMany
    //역으로 member을 조회할때, member을 참조하는 sales 객체를 전부 가져올 수 있게함
    //실제로 기능에 적용하진 않았으나,
    //테이블끼리 관계파악이 용이 + orphan removal에 사용할 수 있으므로 남겨둠
    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Sales> sales = new ArrayList<>();
}
