package com.example.board.controller;

import com.example.board.controller.dto.CreatePostRequest;
import com.example.board.post.Post;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Post create(@RequestBody CreatePostRequest request){
        return postService.createPost(request.getTitle(),
                request.getContent()
        );
    }
    @GetMapping("/posts")//전체 조회
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("/posts/{id}")//단일 조회
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}
