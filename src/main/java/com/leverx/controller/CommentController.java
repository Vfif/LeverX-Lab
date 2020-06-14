package com.leverx.controller;

import com.leverx.model.Comment;
import com.leverx.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.leverx.controller.ArticleController.AUTHORIZATION;
import static com.leverx.controller.ArticleController.DELETE_SUCCESSFULLY;

@RestController
@RequestMapping("/articles/{article_id}/comments")
public class CommentController {
    private static final String COMMENT_ID = "comment_id";
    private static final String ARTICLE_ID = "article_id";
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public @ResponseBody
    Comment saveComment(@PathVariable(ARTICLE_ID) int articleId,
                        @RequestBody Comment comment,
                        @RequestHeader(AUTHORIZATION) String token) {
        return commentService.save(articleId, comment, token);
    }

    @GetMapping
    public @ResponseBody
    List<Comment> findComments(@PathVariable(ARTICLE_ID) int articleId,
                               @RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return commentService.findAllComments(articleId);
        }
        return commentService.filterComments(articleId, params);
    }

    @GetMapping("/{comment_id}")
    public @ResponseBody
    Comment findComment(@PathVariable(ARTICLE_ID) int articleId,
                        @PathVariable(COMMENT_ID) int commentId) {
        return commentService.findComment(articleId, commentId);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteArticle(@PathVariable(ARTICLE_ID) int articleId,
                                                @PathVariable(COMMENT_ID) int commentId,
                                                @RequestHeader(AUTHORIZATION) String token) {
        commentService.deleteArticle(articleId, commentId, token);
        return ResponseEntity.ok(DELETE_SUCCESSFULLY);
    }
}
