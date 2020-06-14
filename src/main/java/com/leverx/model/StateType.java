package com.leverx.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateType {
    PUBLIC("public"), DRAFT("draft");

    @JsonValue
    private String state;
}
