package com.bumboo.shop.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SoftDelete;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

}
