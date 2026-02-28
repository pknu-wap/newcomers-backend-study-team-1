package com.bumboo.shop.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SoftDelete;

@Entity
@ToString
@Getter
@Setter
@Table(indexes =  @Index(columnList = "title", name="작명"))
@SoftDelete(columnName = "deleted") // 'deleted'라는 컬럼을 생성해 T/F로 관리해줌
public class Item{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String title;
    private Integer price;
    private String username;
    private String image;

}
