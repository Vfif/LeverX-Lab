package com.leverx.controller;

import com.leverx.model.Article;
import com.leverx.model.TagCloudElement;
import com.leverx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<Article> findArticlesByTags(@RequestParam List<String> tags) {
        return tagService.findArticlesByTags(tags);
    }

    @GetMapping("/tags-cloud")
    public List<TagCloudElement> getTagCloud() {
        return tagService.getTagCloud();
    }
}
