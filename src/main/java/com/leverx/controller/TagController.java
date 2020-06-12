package com.leverx.controller;

import com.leverx.model.Article;
import com.leverx.model.TagCloudElement;
import com.leverx.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<Article> findArticlesByTags(@RequestParam List<String> tags) {
        return tagService.findArticlesByTags(tags);
    }

    @GetMapping("/tags-cloud")
    public List<TagCloudElement> getTagCloud() {
        return tagService.getTagCloud();
    }
}
