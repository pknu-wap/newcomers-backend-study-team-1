package com.example.board.service;

import com.example.board.post.Post;
import com.example.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Transactional
    public Post createPost(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }//전체 조회

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }

}