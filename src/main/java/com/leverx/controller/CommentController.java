package com.leverx.controller;

import com.leverx.model.Comment;
import com.leverx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.leverx.controller.ArticleController.AUTHORIZATION;

@RestController
@RequestMapping("/articles/{article_id}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public @ResponseBody
    Comment saveComment(@PathVariable("article_id") int articleId,
                        @RequestBody Comment comment,
                        @RequestHeader(AUTHORIZATION) String token) {
        return commentService.save(articleId, comment, token);
    }

    @GetMapping
    public @ResponseBody
    List<Comment> findComments(@PathVariable("article_id") int articleId,
                               @RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return commentService.findAllComments(articleId);
        }
        return commentService.filterComments(articleId, params);
    }

    @GetMapping("/{comment_id}")
    public @ResponseBody
    Comment findComment(@PathVariable("article_id") int articleId,
                        @PathVariable("comment_id") int commentId) {
        return commentService.findComment(articleId, commentId);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("article_id") int articleId,
                                                @PathVariable("comment_id") int commentId,
                                                @RequestHeader(AUTHORIZATION) String token) {
        commentService.deleteArticle(articleId, commentId, token);
        return ResponseEntity.ok("Successfully delete");
    }
}
