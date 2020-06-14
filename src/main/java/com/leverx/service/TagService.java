package com.leverx.service;

import com.leverx.model.Article;
import com.leverx.model.TagCloudElement;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagService {
    private TagRepository tagRepository;
    private ArticleRepository articleRepository;

    public TagService(TagRepository tagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }

    public List<Article> findArticlesByTags(List<String> tags) {
        return articleRepository.findAllByTags(tags);
    }

    public List<TagCloudElement> getTagCloud() {
        List<Object[]> list = tagRepository.countAllByArticleList();
        return list.stream()
                .map(e -> TagCloudElement.builder()
                        .tagName((String) e[0])
                        .postCount((long) e[1])
                        .build())
                .collect(Collectors.toList());
    }
}
