package com.leverx.repository;

import com.leverx.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByArticleId(int articleId);

    Optional<Comment> findByIdAndArticleId(int id, int articleId);

    List<Comment> findAllByArticleIdAndAuthorId(int articleId, int authorId, Pageable pageable);
}
