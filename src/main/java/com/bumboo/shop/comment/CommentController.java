package com.bumboo.shop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public CommentService.CommentDTO addComment(@RequestBody Comment comment, Authentication auth){

        User user = (User)auth.getPrincipal();
        String username = user.getUsername();
        commentService.addComment(comment,username);
        var result = new CommentService.CommentDTO(comment.getContent(),username);
        return result;
    }
}
