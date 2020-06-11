package com.leverx.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("code")
public class Code implements Serializable {

    private int id;
    private String name;

    public Code(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}