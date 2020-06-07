package com.leverx.service;

import com.leverx.exception.AccessIsDeniedException;
import com.leverx.exception.ResourceNotFoundException;
import com.leverx.model.Article;
import com.leverx.model.SearchCriteria;
import com.leverx.model.StateType;
import com.leverx.repository.ArticleRepository;
import com.leverx.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<Article> findPublicArticles() {
        return articleRepository.findByState(StateType.PUBLIC);
    }

    public Article save(Article article) {
        article.setCreatedDate(new Date());
        article.setUpdatedDate(new Date());
        return articleRepository.save(article);
    }

    public List<Article> findMyArticles(String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token);
        return articleRepository.findAllByAuthorId(authorId);
    }


    public Article editArticle(int articleId, Article article, String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token);
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
        oldArticle.setUpdatedDate(new Date());
        return oldArticle;
    }

    public void deleteArticle(int articleId, String token) {
        int authorId = jwtTokenUtil.getIdFromToken(token);
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article with id = " + articleId + " not found"));
        if (authorId == article.getAuthorId()) {
            articleRepository.delete(article);
        } else {
            throw new AccessIsDeniedException("Only the author of the article can delete the post");
        }
    }

    public List<Article> filterArticles(SearchCriteria criteria) {

    }
}
