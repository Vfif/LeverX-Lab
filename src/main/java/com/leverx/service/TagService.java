package com.leverx.service;

import com.leverx.model.Article;
import com.leverx.model.TagCloudElement;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findArticlesByTags(List<String> tags) {
        return articleRepository.findAllByTags(tags);
    }

    public List<TagCloudElement> getTagCloud() {
        List<Object[]> list = tagRepository.countAllByArticleList();
        return list.stream()
                .map(e -> new TagCloudElement.Builder()
                        .withTagName((String) e[0])
                        .withPostCount((long) e[1])
                        .build())
                .collect(Collectors.toList());
    }
}
