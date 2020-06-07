package com.leverx.repository;

import com.leverx.model.Article;
import com.leverx.model.StateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByState(StateType stateType);

    List<Article> findAllByAuthorId(int authorId);

    Page<Article> findAllByTitleAndAuthorId(String title, int authorId, Pageable pageable);
}
