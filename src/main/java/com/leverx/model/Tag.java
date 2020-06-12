package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
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
}
