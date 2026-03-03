package com.bumboo.shop.sales;

import com.bumboo.shop.Member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;

    public void OrderSave(@ModelAttribute Sales sales,Long user_id, String user_name){
        var member = new Member();
        member.setId(user_id);
        member.setUsername(user_name);

    /* Sales 테이블은 @ManyToOne, @JoinColumn 을 통해서 member DB 와 연결되어 있다
    Sales 테이블의 행 1줄은 member 객체를 포함하고 있으므로,
    유저 정보를 저장하기 위해서는 member객체를 따로 생성하여, Sales 객체에 집어넎어준다
     */
        sales.setMember(member);
        salesRepository.save(sales);
    }

    //@ManyToOne의 문제 2 - 모든 컬럼을 가져온다
    //프론트에게 데이터를 전달 할 때 사용자의 민감한 정보는 노출하지 않도록
    //DTO를 통해서 노출할 데이터를 결정한다
    @Getter
    public static class SalesDTO{
        private String itemName;
        private Integer price;
        private String username;

        public SalesDTO(String itemName, Integer price, String username){
            this.itemName = itemName;
            this.price = price;
            this.username = username;
        }
    }
}

