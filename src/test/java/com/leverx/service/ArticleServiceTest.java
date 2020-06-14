package com.leverx.service;

import com.leverx.model.Article;
import com.leverx.model.StateType;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import com.leverx.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@SpringBootTest
class ArticleServiceTest {
    private static final String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdGl2ZU1AZW1haWwuY29tIiwiZXhwIjoxNjIzNDE1MDg1LCJpYXQiOjE1OTE4NzkwODUsImp0aSI6IjEwIn0.iiv-ilK7n-smmxax2HA2TuqIObPIUMXSVkzPUnBk6gyHneb7jpFWxQfx8BYCWLdzCkbhpfZNdKlO3rN4FSYgug";
    private ArticleService service;
    private Article article;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private TagRepository tagRepository;
    @Autowired
    private TokenUtil tokenUtil;

    @BeforeEach
    public void setUp() {
        article = Article.builder()
                .title("title")
                .text("text")
                .state(StateType.PUBLIC)
                .tags(Collections.emptyList())
                .build();
        MockitoAnnotations.initMocks(this);
        service = new ArticleService(articleRepository, tagRepository, tokenUtil);
    }

    @Test
    void saveTest() {
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArgument(0));

        Article articleAfterSave = service.save(article, token);

        assertNotEquals(articleAfterSave.getAuthorId(), 0);
        assertNotNull(articleAfterSave.getCreatedDate());
        assertNotNull(articleAfterSave.getUpdatedDate());
    }

    @Test
    void editArticle() {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        article.setAuthorId(authorId);
        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArgument(0));

        Article draftArticle = new Article();
        draftArticle.setState(StateType.DRAFT);

        Article articleAfterEdit = service.editArticle(anyInt(), draftArticle, token);
        assertEquals(articleAfterEdit.getState(), StateType.DRAFT);
    }
}