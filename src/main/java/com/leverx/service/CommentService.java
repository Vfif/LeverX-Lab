package com.leverx.service;

import com.leverx.exception.AccessIsDeniedException;
import com.leverx.exception.ResourceNotFoundException;
import com.leverx.model.Comment;
import com.leverx.repository.CommentRepository;
import com.leverx.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private TokenUtil tokenUtil;

    public CommentService(CommentRepository commentRepository, TokenUtil tokenUtil) {
        this.commentRepository = commentRepository;
        this.tokenUtil = tokenUtil;
    }

    public List<Comment> findAllComments(int articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    public Comment save(int articleId, Comment comment, String token) {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        comment.setAuthorId(authorId);
        comment.setArticleId(articleId);
        comment.setCreatedDate(new Date());
        return commentRepository.save(comment);
    }

    public Comment findComment(int articleId, int commentId) {
        return commentRepository.findByIdAndArticleId(commentId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment with id = " + commentId + " and article id = " + articleId + " not found"));
    }

    public void deleteArticle(int articleId, int commentId, String token) {
        int authorId = tokenUtil.getIdFromToken(token.substring(7));
        Comment comment = commentRepository
                .findByIdAndArticleId(commentId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment with id = " + commentId + " and article id = " + articleId + " not found"));
        if (authorId == comment.getAuthorId()) {
            commentRepository.delete(comment);
        } else {
            throw new AccessIsDeniedException("Only the author of the comment can delete the post");
        }
    }

    public List<Comment> filterComments(int articleId, Map<String, String> params) {
        int skip = Integer.parseInt(params.get("skip"));
        int limit = Integer.parseInt(params.get("limit"));
        int authorId = Integer.parseInt(params.get("author"));
        String sortField = params.get("sort");
        Sort.Direction order = Sort.Direction.fromString(params.get("order"));

        Pageable sortAndPagination = PageRequest.of(skip, limit, order, sortField);
        return commentRepository.findAllByArticleIdAndAuthorId(articleId, authorId, sortAndPagination);
    }
}
