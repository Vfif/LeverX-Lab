package com.leverx.controller;

import com.leverx.model.Article;
import com.leverx.repository.ArticleRepository;
import com.leverx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository articleService;

    @PutMapping("/{id}")
    public String editArticleById(@PathVariable int id) {
        Optional<Article> article = articleService.findById(1);
        return "good";
    }

}
