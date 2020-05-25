package com.leverx.controller;

import com.leverx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PutMapping("/{id}")
    public String editArticleById(@PathVariable int id, Principal principal) {
        return "good";
    }

}
