package com.leverx.service;

import com.leverx.model.Article;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findArticlesByTags(List<String> tags) {
        return articleRepository.findAllByTags(tags);
    }
}
