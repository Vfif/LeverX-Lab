package com.leverx.controller;

import com.leverx.model.Article;
import com.leverx.model.SearchCriteria;
import com.leverx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leverx.controller.CommentController.AUTHORIZATION;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PutMapping("/{id}")
    public @ResponseBody
    Article editArticleById(@PathVariable int id,
                            @RequestBody Article article,
                            @RequestHeader(AUTHORIZATION) String token) {
        return articleService.editArticle(id, article, token);
    }

    @PostMapping
    public @ResponseBody
    Article savePost(@RequestBody Article article,
                     @RequestHeader(AUTHORIZATION) String token) {
        return articleService.save(article, token);
    }

    @GetMapping
    public @ResponseBody
    List<Article> findPublicArticles() {
        return articleService.findPublicArticles();
    }

    @GetMapping("/my")
    public @ResponseBody
    List<Article> findMyArticles(@RequestHeader(AUTHORIZATION) String token) {
        return articleService.findMyArticles(token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable int id,
                                                @RequestHeader(AUTHORIZATION) String token) {
        articleService.deleteArticle(id, token);
        return ResponseEntity.ok("Successfully delete");
    }

    @GetMapping("/filter")
    public Page<Article> filterArticles(@RequestBody SearchCriteria criteria) {
        return articleService.filterArticles(criteria);
    }
}
