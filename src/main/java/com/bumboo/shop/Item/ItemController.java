package com.bumboo.shop.Item;

import com.bumboo.shop.comment.Comment;
import com.bumboo.shop.comment.CommentRepository;
import com.bumboo.shop.sales.Sales;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 현재 쇼핑몰 프로젝트 개인 연습으로 80% 이상 진행 완료된 상태입니다.
 스터디 기본 제시 범위(기본 CRUD 및 계층 분리)에 더해서
 선택 사항인 DTO / Entity 분리 ,외부 DB(MySQL) 등이 일부 적용됨을 알립니다.

 추가로 쇼핑몰 기능 구현에 필요한 회원 기능(Spring Security를 통한 세션 방식),
 이미지 등록을 위한 AWS S3 호스팅,
 검색기능 ( 페이지네이션 + 효율적인 검색을 위한 full text index),
 상품 상세페이지에 댓글 기능 추가를 완료하였으며

 현재는 상품 주문기능 + 제2 정규화를 적용한 DB를 구현하고 있습니다.
 일단 진행 완료한 상태까지만 업로드 하고, 추가 내용을 덧붙여서 프로젝트를 점차 완성해가겠습니다.
 */

@Controller
@RequiredArgsConstructor // 생성자 주입을 통해 의존성 주입(DI)의 안정성 확보
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    //(데이터베이스 정규화 및 연관관계 표현) 게시물 하나에 종속되는 여러가지 댓글들의 리포지토리를 주입해서 사용.
    private final CommentRepository commentRepository;

    /*AWS s3 호스팅 방식으로 이미지를 업로드 하는 이유:presigned-url 방식의 우수성
    1. 서버의 부하를 낮춘다 - 무거운 이미지 파일 대신 가벼운 주소를 저장
    2. 보안 - 허가된 사용자에게, 정해지 시간 동안만 업로드 권한을 제공하되, 외부에 공개되지 않는다.
    3. 비용 절감 - 실제 저장된 데이터 용량과 요청 수에 대해서만 과금
    */
    private final S3Service s3Service;

    /*
     상품 목록 조회 (Paging 적용)
     Spring Data JPA의 Pageable을 활용하여 대용량 데이터 조회 성능을 고려함
     Pageable = 인터페이스, 페이지네이션의 정보를 담는 추상적 표준 인터페이스.
     PageRequset.of( page,size,sort ) 함수 호출을 통해 생성한다.

     -> 호출을 통해 생성된 결과는 Page<> or Slice<>로 받는다.
     차이점 : 전체 페이지 수를 담은 정보의 유무
     Page = 유, 게시판형에 적합, 데이터 쿼리 + 전체 개수 정보 Count 쿼리를 포함한다
     - getTotalPages(), getTotalElements(), getNumbers() , getContent()의 함수를 사용 가능

     Slice = 무, 더보기/무한 스크롤형에 적합, 전체 개수에 대한 정보가 없다. 대신 "다음 데이터" 존재 유무는 파악 가능하다
     - hasNext() , isFirst(), isLast() 사용 가능
     */


    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Item> result;

        result = itemRepository.findPageBy(pageable);
        model.addAttribute("searchKeyword", null);
        model.addAttribute("items", result.getContent());
        model.addAttribute("pageCount", result.getTotalPages());
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }
    /*
    상품 등록 처리
    Authentication 객체를 직접 활용하여 유저 정보 획득
    Authentication = Principal + (Authenticated : true , Authorities)
    컨테이너          실제 인물 정보가       권한 정보들
    (스프링 시큐리티   담긴 객체
    공식 문서)

    역할(Role) vs 권한(Authority) 의 구분

    역할 : 사용자의 신분/계급(누구인가?)
    반드시 ROLE_로 시작(접두사) .hasRole("ADMIN")

    권한 : 사용자의 세부 동작 제어(무엇이 가능한가?)
    접두사 없음 .hasAuthority("READ_POST")
    */
    @PostMapping("/add")
    String addPost(@ModelAttribute Item item, Authentication auth) {
        User user = (User)auth.getPrincipal();
        //Authentication 파라미터에서(컨테이너) 유저장보(Principal)을 뽑기

        itemService.saveItem(item,user.getUsername());
        //Principal에서 유저 정보의 일부를 뽑아서 함수의 파라미터로 전달함
        //유저 정보는 html페이지에서 조작할 수 없도록, 서버에서 직접 다루는 것이 보안상 권장된다.
        return "redirect:/list";
    }


    /* 사용자 - 서버 간 전송 수단
    1.URL parameter 2.Query String 3.HTML form 4.AJAX/JSON

    1.@PathVariable url형태)/item/{id} - 경로변수, @PathVariable Long id 파라미터로 받는다.
    2.@RequestParam url형태)/search?q=abc - 쿼리 스트링, @RequestParam Long q로 받는다.
    3.@ModelAttribute 형태)req.body - 사용자가 전송하는 여러 데이터를 자바 객체(DTO/Entity)로 한 번에 변환해준다(new 객체 + Setter호출).
    4.@RequestBody 형태)req.body - http body에 담긴 JSON 데이터를 자바 객체로 변환해준다 AJAX요청에 필수적.

     */
    @GetMapping("/detail/{id}")
    String showDetail(Model model,@PathVariable Long id) throws Exception{
        /* 상세 페이지 조회 및 예외 처리
         Optional의 orElseThrow를 활용하여 Null 안전성(NPE 방지) 확보 */
        Item result = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다."));
        List<Comment> comments = commentRepository.findByParentIdOrderByCreatedAtAsc(id);

        model.addAttribute("item", result);
        model.addAttribute("parentId",id);
        model.addAttribute("comments",comments);
        return "detail.html";
    }

    /* 상품 수정 페이지 이동
     기존 데이터를 Model에 담아 Thymeleaf의 th:value 등에 바인딩하기 위함
     */
    @GetMapping("/edit/{id}")
    String edit(Model model,@PathVariable Long id) throws Exception{
        Item result = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다."));
        model.addAttribute("pre",result);
        return "edit.html";
    }

    /*
     상품 수정 처리 (@ModelAttribute 활용)
     수정 시에는 전체 필드를 갈아끼우는 방식보다는,
     필요한 필드만 변경하는 JPA의 Dirty Checking(변경 감지) 기능을 Service 레이어에서 활용하도록 설계
     */
    @PostMapping("/edit/{id}")
    String editPost(@ModelAttribute Item item, @PathVariable Long id){
        itemService.updateItem(item,id);
        return "redirect:/list";
    }

    /*
     상품 삭제 처리 (AJAX 대응)
     AJAX를 쓰기에 적합한 형태 : html페이지 전체 구조가 바뀌는게 아닌 일부 요소만 수정이 필요 할때
     대표적으로 삭제기능, 상품 list페이지 중 1개 상품 게시물만 안보이게 하면 됌.

     ResponseEntity를 반환하여 HTTP 상태 코드(200 ok)와 메시지를 클라이언트(JS)에 직접 전달
     비동기 요청(AJAX/Fetch) 처리에 적합한 방식
     */
    @DeleteMapping("/delete")
     ResponseEntity deletePost(@RequestParam Long docid) {
        itemService.deleteItem(docid);
        return ResponseEntity.ok("삭제 성공");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename){
        // 클라이언트 보안을 위해 서버를 거치지 않고 S3로 직접 업로드하는 Presigned URL 방식 도입
        String result = s3Service.createPresignedUrl("test/" + filename);
        //@ResponseBody를 통해 생성된 Presigned URL을 유저(JS)에 전달
        return result;
    }

    /*
     Full-Text Search (검색) 구현
     @PageableDefault: 파라미터가 없을 경우 기본 페이징 설정(size=5) 적용
     Binary Search를 적용 가능하기 때문에, 성능이 우수한 Full-Text Index를 활용한 검색 로직
     검색 결과 또한 페이징 처리를 적용
     */
    @GetMapping("/search")
    public String search(@RequestParam String keyword, @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Item> resultPage = itemRepository.fullTextSearch(keyword, pageable);

        model.addAttribute("items", resultPage.getContent());
        model.addAttribute("pageCount", resultPage.getTotalPages());
        model.addAttribute("searchKeyword", keyword);
        return "list.html";
    }

    @PostMapping("/search")
    public String searchPage(@RequestParam String keyword, @PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<Item> resultPage = itemRepository.fullTextSearch(keyword, pageable);

        model.addAttribute("items", resultPage.getContent());
        model.addAttribute("pageCount", resultPage.getTotalPages());
        model.addAttribute("searchKeyword", keyword);
        return "list.html";
    }


}

