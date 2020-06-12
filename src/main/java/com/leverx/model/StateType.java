package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StateType {
    PUBLIC("public"), DRAFT("draft");

    private String state;

    @JsonValue
    public String getState() {
        return state;
    }
}
