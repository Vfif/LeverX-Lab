package com.leverx.controller;

import com.leverx.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class HelloWorldController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping({"/hello"})
    public String hello() {
        return "Hello World";
    }

}
