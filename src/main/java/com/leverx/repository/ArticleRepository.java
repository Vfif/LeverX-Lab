package com.leverx.repository;

import com.leverx.model.Article;
import com.leverx.model.StateType;
import com.leverx.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByState(StateType stateType);

    List<Article> findAllByAuthorId(int authorId);

    List<Article> findAllByTitleAndAuthorIdAndState(String title, int authorId, StateType stateType, Pageable pageable);

    @Query("select distinct a from Article a join a.tags t where t.name in :name")
    List<Article> findAllByTags(@Param("name") List<String> tags);
}
