package com.leverx.controller;

import com.leverx.util.JwtTokenUtil;
import com.leverx.model.Comment;
import com.leverx.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/articles/{id}/comments")
public class CommentController {
    public static final String AUTHORIZATION = "authorization";
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public String addCommentToArticle(@PathVariable("id") int articleId,
                                      @RequestBody Comment comment,
                                      @RequestHeader(AUTHORIZATION) String token) {
        int userId = jwtTokenUtil.getIdFromToken(token.substring(7));
        comment.setArticleId(articleId);
        comment.setCreatedDate(new java.sql.Date(new Date().getTime()));
        return "good";
    }

    @GetMapping
    public List<Comment> findAllCommentsByArticleId(@PathVariable int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

   /* @PutMapping
    public String editArticleById(@PathVariable int id) {
        return "good";
    }

    @PutMapping
    public String editArticleById(@PathVariable int id) {
        return "good";
    }*/

}
