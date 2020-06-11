package com.leverx.controller;

import com.leverx.model.Article;
import com.leverx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    public static final String AUTHORIZATION = "authorization";

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
    List<Article> findAllArticles(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return articleService.findPublicArticles();
        }
        return articleService.filterPublicArticles(params);
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
}
