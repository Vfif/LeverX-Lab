package com.leverx.service;

import com.leverx.exception.AccessIsDeniedException;
import com.leverx.exception.ResourceNotFoundException;
import com.leverx.model.Article;
import com.leverx.model.SearchCriteria;
import com.leverx.model.StateType;
import com.leverx.model.Tag;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import com.leverx.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<Article> findPublicArticles() {
        return articleRepository.findByState(StateType.PUBLIC);
    }

    public Article save(Article article, String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token.substring(7));
        article.setAuthorId(authorId);
        article.setCreatedDate(new Date());
        article.setUpdatedDate(new Date());
        return articleRepository.save(article);
    }

    public List<Article> findMyArticles(String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token.substring(7));
        return articleRepository.findAllByAuthorId(authorId);
    }


    public Article editArticle(int articleId, Article article, String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token.substring(7));
        Article existedArticle = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article with id = " + articleId + " not found"));
        if (authorId == existedArticle.getAuthorId()) {
            article = merge(existedArticle, article);
            return articleRepository.save(article);
        } else {
            throw new AccessIsDeniedException("Only the author of the article can edit the post");
        }
    }

    private void saveArticleTags(Article article) {
        List<Tag> tagList = article.getTags();
        List<Tag> newTagList = tagList.stream()
                .map(tag -> tagRepository
                        .findByName(tag.getName())
                        .orElse(tag))
                .collect(Collectors.toList());
        article.setTags(newTagList);
    }

    private Article merge(Article oldArticle, Article newArticle) {
        if (newArticle.getTitle() != null) {
            oldArticle.setTitle(newArticle.getTitle());
        }
        if (newArticle.getText() != null) {
            oldArticle.setText(newArticle.getText());
        }
        if (newArticle.getAuthorId() != 0) {
            oldArticle.setAuthorId(newArticle.getAuthorId());
        }
        if (newArticle.getState() != null) {
            oldArticle.setState(newArticle.getState());
        }
        if (newArticle.getTags() != null) {
            saveArticleTags(newArticle);
            oldArticle.setTags(newArticle.getTags());
        }
        oldArticle.setUpdatedDate(new Date());
        return oldArticle;
    }

    public void deleteArticle(int articleId, String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token.substring(7));
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article with id = " + articleId + " not found"));
        if (authorId == article.getAuthorId()) {
            articleRepository.delete(article);
        } else {
            throw new AccessIsDeniedException("Only the author of the article can delete the post");
        }
    }

    public Page<Article> filterArticles(SearchCriteria criteria) {
        Pageable sortAndPagination =
                PageRequest.of(criteria.getSkip(),
                        criteria.getLimit(),
                        criteria.getOrder(),
                        criteria.getSort());
        return articleRepository.findAllByTitleAndAuthorId(
                criteria.getQ(),
                criteria.getAuthor(),
                sortAndPagination);
    }
}
