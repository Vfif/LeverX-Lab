package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StateType {
    PUBLIC("public"), DRAFT("draft");

    private String state;

    StateType(String state) {
        this.state = state;
    }

    @JsonValue
    public String getState() {
        return state;
    }
}
