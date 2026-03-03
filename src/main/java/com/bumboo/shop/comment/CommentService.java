package com.bumboo.shop.comment;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public void addComment(@ModelAttribute Comment comment, String username){
        comment.setUsername(username);
        commentRepository.save(comment);
    }

    @Getter
    public static class CommentDTO{
        private String content;
        private String username;
        public CommentDTO(String content, String username){
            this.content = content;
            this.username = username;
        }
    }
}
