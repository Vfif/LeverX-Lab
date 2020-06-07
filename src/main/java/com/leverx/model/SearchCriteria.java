package com.leverx.model;

import org.springframework.data.domain.Sort;

public class SearchCriteria {
    private int skip;
    private int limit;
    private String q;
    private int author;
    private String sort;
    private Sort.Direction order;

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Sort.Direction getOrder() {
        return order;
    }

    public void setOrder(Sort.Direction order) {
        this.order = order;
    }
}
