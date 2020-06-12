package com.leverx.service;

import com.leverx.exception.AccessIsDeniedException;
import com.leverx.exception.ResourceNotFoundException;
import com.leverx.model.Article;
import com.leverx.model.StateType;
import com.leverx.model.Tag;
import com.leverx.repository.ArticleRepository;
import com.leverx.repository.TagRepository;
import com.leverx.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;
    private TokenUtil tokenUtil;

    public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository, TokenUtil tokenUtil) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.tokenUtil = tokenUtil;
    }

    public List<Article> findPublicArticles() {
        return articleRepository.findByState(StateType.PUBLIC);
    }

    public Article save(Article article, String token) {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        article.setAuthorId(authorId);
        article.setCreatedDate(new Date());
        article.setUpdatedDate(new Date());
        saveArticleTags(article);
        return articleRepository.save(article);
    }

    public List<Article> findMyArticles(String token) {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        return articleRepository.findAllByAuthorId(authorId);
    }


    public Article editArticle(int articleId, Article article, String token) {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
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
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article with id = " + articleId + " not found"));
        if (authorId == article.getAuthorId()) {
            articleRepository.delete(article);
        } else {
            throw new AccessIsDeniedException("Only the author of the article can delete the post");
        }
    }

    public List<Article> filterPublicArticles(Map<String, String> params) {
        int skip = Integer.parseInt(params.get("skip"));
        int limit = Integer.parseInt(params.get("limit"));
        String postTitle = params.get("q");
        int authorId = Integer.parseInt(params.get("author"));
        String sortField = params.get("sort");
        Sort.Direction order = Sort.Direction.fromString(params.get("order"));

        Pageable sortAndPagination = PageRequest.of(skip, limit, order, sortField);
        return articleRepository.findAllByTitleAndAuthorIdAndState(postTitle, authorId, StateType.PUBLIC, sortAndPagination);
    }
}
