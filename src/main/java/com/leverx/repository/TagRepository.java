package com.leverx.repository;

import com.leverx.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);

    @Query("select t.name, count(a) from Article a join a.tags t GROUP BY t.name")
    List<Object[]> countAllByArticleList();
}
