package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<Article> articleList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
