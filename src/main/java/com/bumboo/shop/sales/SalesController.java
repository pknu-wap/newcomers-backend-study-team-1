package com.bumboo.shop.sales;

import com.bumboo.shop.Item.Item;
import com.bumboo.shop.Item.ItemRepository;
import com.bumboo.shop.Member.Member;
import com.bumboo.shop.Member.MemberRepository;
import com.bumboo.shop.Member.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final ItemRepository itemRepository;
    private final SalesService salesService;
    private final SalesRepository salesRepository;

    @GetMapping("/order/{id}")
    public String orderPage(@PathVariable Long id, Model model){
        Item result = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다."));
        model.addAttribute("item",result);
        return "orderPage.html";
    }

    //사용자가 form에서 전달하는 데이터 : 상품명,총 가격,상품 갯수
    //sales객체에 추가로 넣을 정보 : 로그인된 유저 document의 id(PK)와 username.
    //로그인 관련 정보는 보안상 서버에서 추출하여 전달한다.
    @PostMapping("/order")
    public String RequestOrder(@ModelAttribute Sales sales, Model model,
       @AuthenticationPrincipal MemberService.CustomUser customUser)
    {
     //getId()라는 함수는 기본 제공되지 않으므로, member 서비스 레이어에 추가하였다
        Long user_id = customUser.getId();
        String user_name = customUser.getUsername();

        salesService.OrderSave(sales,user_id,user_name);

        model.addAttribute("sales",sales);
        return "myPage.html";
    }

    //모든 주문정보 조회하는 페이지(추후 관라지만 접속 가능하도록 예정)
    @GetMapping("/order/all")
    String getOrderAll(Model model){

    //@ManyToOne의 성능 문제 - N + 1 을 해결하기 위해서
    //레포지토리에 JOIN FETCH를 적용한 customFindAll함수를 만들었다.
        List<Sales> result = salesRepository.customFindAll();
        model.addAttribute("sales",result);
        return "Admin.html";
    }
}
